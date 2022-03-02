import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConsumptionDetailsService } from '../service/consumption-details.service';
import { IConsumptionDetails, ConsumptionDetails } from '../consumption-details.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';

import { ConsumptionDetailsUpdateComponent } from './consumption-details-update.component';

describe('ConsumptionDetails Management Update Component', () => {
  let comp: ConsumptionDetailsUpdateComponent;
  let fixture: ComponentFixture<ConsumptionDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let consumptionDetailsService: ConsumptionDetailsService;
  let productInventoryService: ProductInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConsumptionDetailsUpdateComponent],
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
      .overrideTemplate(ConsumptionDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConsumptionDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    consumptionDetailsService = TestBed.inject(ConsumptionDetailsService);
    productInventoryService = TestBed.inject(ProductInventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductInventory query and add missing value', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const productInventory: IProductInventory = { id: 24248 };
      consumptionDetails.productInventory = productInventory;

      const productInventoryCollection: IProductInventory[] = [{ id: 64553 }];
      jest.spyOn(productInventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: productInventoryCollection })));
      const additionalProductInventories = [productInventory];
      const expectedCollection: IProductInventory[] = [...additionalProductInventories, ...productInventoryCollection];
      jest.spyOn(productInventoryService, 'addProductInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(productInventoryService.query).toHaveBeenCalled();
      expect(productInventoryService.addProductInventoryToCollectionIfMissing).toHaveBeenCalledWith(
        productInventoryCollection,
        ...additionalProductInventories
      );
      expect(comp.productInventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const productInventory: IProductInventory = { id: 53159 };
      consumptionDetails.productInventory = productInventory;

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(consumptionDetails));
      expect(comp.productInventoriesSharedCollection).toContain(productInventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = { id: 123 };
      jest.spyOn(consumptionDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumptionDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(consumptionDetailsService.update).toHaveBeenCalledWith(consumptionDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = new ConsumptionDetails();
      jest.spyOn(consumptionDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumptionDetails }));
      saveSubject.complete();

      // THEN
      expect(consumptionDetailsService.create).toHaveBeenCalledWith(consumptionDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = { id: 123 };
      jest.spyOn(consumptionDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(consumptionDetailsService.update).toHaveBeenCalledWith(consumptionDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductInventoryById', () => {
      it('Should return tracked ProductInventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
