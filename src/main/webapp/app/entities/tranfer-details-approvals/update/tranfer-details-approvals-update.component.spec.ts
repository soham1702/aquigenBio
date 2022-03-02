import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TranferDetailsApprovalsService } from '../service/tranfer-details-approvals.service';
import { ITranferDetailsApprovals, TranferDetailsApprovals } from '../tranfer-details-approvals.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

import { TranferDetailsApprovalsUpdateComponent } from './tranfer-details-approvals-update.component';

describe('TranferDetailsApprovals Management Update Component', () => {
  let comp: TranferDetailsApprovalsUpdateComponent;
  let fixture: ComponentFixture<TranferDetailsApprovalsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tranferDetailsApprovalsService: TranferDetailsApprovalsService;
  let transferService: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TranferDetailsApprovalsUpdateComponent],
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
      .overrideTemplate(TranferDetailsApprovalsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TranferDetailsApprovalsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tranferDetailsApprovalsService = TestBed.inject(TranferDetailsApprovalsService);
    transferService = TestBed.inject(TransferService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Transfer query and add missing value', () => {
      const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 456 };
      const transfer: ITransfer = { id: 49802 };
      tranferDetailsApprovals.transfer = transfer;

      const transferCollection: ITransfer[] = [{ id: 40651 }];
      jest.spyOn(transferService, 'query').mockReturnValue(of(new HttpResponse({ body: transferCollection })));
      const additionalTransfers = [transfer];
      const expectedCollection: ITransfer[] = [...additionalTransfers, ...transferCollection];
      jest.spyOn(transferService, 'addTransferToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tranferDetailsApprovals });
      comp.ngOnInit();

      expect(transferService.query).toHaveBeenCalled();
      expect(transferService.addTransferToCollectionIfMissing).toHaveBeenCalledWith(transferCollection, ...additionalTransfers);
      expect(comp.transfersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 456 };
      const transfer: ITransfer = { id: 13190 };
      tranferDetailsApprovals.transfer = transfer;

      activatedRoute.data = of({ tranferDetailsApprovals });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tranferDetailsApprovals));
      expect(comp.transfersSharedCollection).toContain(transfer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferDetailsApprovals>>();
      const tranferDetailsApprovals = { id: 123 };
      jest.spyOn(tranferDetailsApprovalsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferDetailsApprovals }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tranferDetailsApprovalsService.update).toHaveBeenCalledWith(tranferDetailsApprovals);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferDetailsApprovals>>();
      const tranferDetailsApprovals = new TranferDetailsApprovals();
      jest.spyOn(tranferDetailsApprovalsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferDetailsApprovals }));
      saveSubject.complete();

      // THEN
      expect(tranferDetailsApprovalsService.create).toHaveBeenCalledWith(tranferDetailsApprovals);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferDetailsApprovals>>();
      const tranferDetailsApprovals = { id: 123 };
      jest.spyOn(tranferDetailsApprovalsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tranferDetailsApprovalsService.update).toHaveBeenCalledWith(tranferDetailsApprovals);
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
