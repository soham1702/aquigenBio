import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RawMaterialOrderService } from '../service/raw-material-order.service';
import { IRawMaterialOrder, RawMaterialOrder } from '../raw-material-order.model';

import { RawMaterialOrderUpdateComponent } from './raw-material-order-update.component';

describe('RawMaterialOrder Management Update Component', () => {
  let comp: RawMaterialOrderUpdateComponent;
  let fixture: ComponentFixture<RawMaterialOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rawMaterialOrderService: RawMaterialOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RawMaterialOrderUpdateComponent],
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
      .overrideTemplate(RawMaterialOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RawMaterialOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rawMaterialOrderService = TestBed.inject(RawMaterialOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const rawMaterialOrder: IRawMaterialOrder = { id: 456 };

      activatedRoute.data = of({ rawMaterialOrder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rawMaterialOrder));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialOrder>>();
      const rawMaterialOrder = { id: 123 };
      jest.spyOn(rawMaterialOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterialOrder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rawMaterialOrderService.update).toHaveBeenCalledWith(rawMaterialOrder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialOrder>>();
      const rawMaterialOrder = new RawMaterialOrder();
      jest.spyOn(rawMaterialOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterialOrder }));
      saveSubject.complete();

      // THEN
      expect(rawMaterialOrderService.create).toHaveBeenCalledWith(rawMaterialOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialOrder>>();
      const rawMaterialOrder = { id: 123 };
      jest.spyOn(rawMaterialOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rawMaterialOrderService.update).toHaveBeenCalledWith(rawMaterialOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
