import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TranferRecievedService } from '../service/tranfer-recieved.service';
import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

import { TranferRecievedUpdateComponent } from './tranfer-recieved-update.component';

describe('TranferRecieved Management Update Component', () => {
  let comp: TranferRecievedUpdateComponent;
  let fixture: ComponentFixture<TranferRecievedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tranferRecievedService: TranferRecievedService;
  let transferService: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TranferRecievedUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TranferRecievedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TranferRecievedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tranferRecievedService = TestBed.inject(TranferRecievedService);
    transferService = TestBed.inject(TransferService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Transfer query and add missing value', () => {
      const tranferRecieved: ITranferRecieved = { id: 456 };
      const transfer: ITransfer = { id: 73554 };
      tranferRecieved.transfer = transfer;

      const transferCollection: ITransfer[] = [{ id: 44733 }];
      jest.spyOn(transferService, 'query').mockReturnValue(of(new HttpResponse({ body: transferCollection })));
      const additionalTransfers = [transfer];
      const expectedCollection: ITransfer[] = [...additionalTransfers, ...transferCollection];
      jest.spyOn(transferService, 'addTransferToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      expect(transferService.query).toHaveBeenCalled();
      expect(transferService.addTransferToCollectionIfMissing).toHaveBeenCalledWith(transferCollection, ...additionalTransfers);
      expect(comp.transfersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tranferRecieved: ITranferRecieved = { id: 456 };
      const transfer: ITransfer = { id: 5932 };
      tranferRecieved.transfer = transfer;

      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tranferRecieved));
      expect(comp.transfersSharedCollection).toContain(transfer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = { id: 123 };
      jest.spyOn(tranferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferRecieved }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tranferRecievedService.update).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = new TranferRecieved();
      jest.spyOn(tranferRecievedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferRecieved }));
      saveSubject.complete();

      // THEN
      expect(tranferRecievedService.create).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = { id: 123 };
      jest.spyOn(tranferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tranferRecievedService.update).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTransferById', () => {
      it('Should return tracked Transfer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransferById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
