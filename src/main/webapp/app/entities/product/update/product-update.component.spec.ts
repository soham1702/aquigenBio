import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductService } from '../service/product.service';
import { IProduct, Product } from '../product.model';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { RawMaterialOrderService } from 'app/entities/raw-material-order/service/raw-material-order.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { ProductUpdateComponent } from './product-update.component';

describe('Product Management Update Component', () => {
  let comp: ProductUpdateComponent;
  let fixture: ComponentFixture<ProductUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productService: ProductService;
  let categoriesService: CategoriesService;
  let unitService: UnitService;
  let rawMaterialOrderService: RawMaterialOrderService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductUpdateComponent],
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
      .overrideTemplate(ProductUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productService = TestBed.inject(ProductService);
    categoriesService = TestBed.inject(CategoriesService);
    unitService = TestBed.inject(UnitService);
    rawMaterialOrderService = TestBed.inject(RawMaterialOrderService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call categories query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const categories: ICategories = { id: 5037 };
      product.categories = categories;

      const categoriesCollection: ICategories[] = [{ id: 42362 }];
      jest.spyOn(categoriesService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriesCollection })));
      const expectedCollection: ICategories[] = [categories, ...categoriesCollection];
      jest.spyOn(categoriesService, 'addCategoriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(categoriesService.query).toHaveBeenCalled();
      expect(categoriesService.addCategoriesToCollectionIfMissing).toHaveBeenCalledWith(categoriesCollection, categories);
      expect(comp.categoriesCollection).toEqual(expectedCollection);
    });

    it('Should call unit query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const unit: IUnit = { id: 82468 };
      product.unit = unit;

      const unitCollection: IUnit[] = [{ id: 13061 }];
      jest.spyOn(unitService, 'query').mockReturnValue(of(new HttpResponse({ body: unitCollection })));
      const expectedCollection: IUnit[] = [unit, ...unitCollection];
      jest.spyOn(unitService, 'addUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(unitService.query).toHaveBeenCalled();
      expect(unitService.addUnitToCollectionIfMissing).toHaveBeenCalledWith(unitCollection, unit);
      expect(comp.unitsCollection).toEqual(expectedCollection);
    });

    it('Should call RawMaterialOrder query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const rawMaterialOrders: IRawMaterialOrder[] = [{ id: 41733 }];
      product.rawMaterialOrders = rawMaterialOrders;

      const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 3138 }];
      jest.spyOn(rawMaterialOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: rawMaterialOrderCollection })));
      const additionalRawMaterialOrders = [...rawMaterialOrders];
      const expectedCollection: IRawMaterialOrder[] = [...additionalRawMaterialOrders, ...rawMaterialOrderCollection];
      jest.spyOn(rawMaterialOrderService, 'addRawMaterialOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(rawMaterialOrderService.query).toHaveBeenCalled();
      expect(rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing).toHaveBeenCalledWith(
        rawMaterialOrderCollection,
        ...additionalRawMaterialOrders
      );
      expect(comp.rawMaterialOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const securityUser: ISecurityUser = { id: 45888 };
      product.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 71384 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const product: IProduct = { id: 456 };
      const categories: ICategories = { id: 17858 };
      product.categories = categories;
      const unit: IUnit = { id: 92857 };
      product.unit = unit;
      const rawMaterialOrders: IRawMaterialOrder = { id: 72534 };
      product.rawMaterialOrders = [rawMaterialOrders];
      const securityUser: ISecurityUser = { id: 56495 };
      product.securityUser = securityUser;

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(product));
      expect(comp.categoriesCollection).toContain(categories);
      expect(comp.unitsCollection).toContain(unit);
      expect(comp.rawMaterialOrdersSharedCollection).toContain(rawMaterialOrders);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Product>>();
      const product = { id: 123 };
      jest.spyOn(productService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: product }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productService.update).toHaveBeenCalledWith(product);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Product>>();
      const product = new Product();
      jest.spyOn(productService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: product }));
      saveSubject.complete();

      // THEN
      expect(productService.create).toHaveBeenCalledWith(product);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Product>>();
      const product = { id: 123 };
      jest.spyOn(productService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productService.update).toHaveBeenCalledWith(product);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCategoriesById', () => {
      it('Should return tracked Categories primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoriesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUnitById', () => {
      it('Should return tracked Unit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUnitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRawMaterialOrderById', () => {
      it('Should return tracked RawMaterialOrder primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRawMaterialOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedRawMaterialOrder', () => {
      it('Should return option if no RawMaterialOrder is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedRawMaterialOrder(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected RawMaterialOrder for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedRawMaterialOrder(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this RawMaterialOrder is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedRawMaterialOrder(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
