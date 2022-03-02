import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QuatationDetailsService } from '../service/quatation-details.service';
import { IQuatationDetails, QuatationDetails } from '../quatation-details.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IProductQuatation } from 'app/entities/product-quatation/product-quatation.model';
import { ProductQuatationService } from 'app/entities/product-quatation/service/product-quatation.service';

import { QuatationDetailsUpdateComponent } from './quatation-details-update.component';

describe('QuatationDetails Management Update Component', () => {
  let comp: QuatationDetailsUpdateComponent;
  let fixture: ComponentFixture<QuatationDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let quatationDetailsService: QuatationDetailsService;
  let productService: ProductService;
  let unitService: UnitService;
  let categoriesService: CategoriesService;
  let productQuatationService: ProductQuatationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QuatationDetailsUpdateComponent],
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
      .overrideTemplate(QuatationDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QuatationDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    quatationDetailsService = TestBed.inject(QuatationDetailsService);
    productService = TestBed.inject(ProductService);
    unitService = TestBed.inject(UnitService);
    categoriesService = TestBed.inject(CategoriesService);
    productQuatationService = TestBed.inject(ProductQuatationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call product query and add missing value', () => {
      const quatationDetails: IQuatationDetails = { id: 456 };
      const product: IProduct = { id: 76044 };
      quatationDetails.product = product;

      const productCollection: IProduct[] = [{ id: 76819 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const expectedCollection: IProduct[] = [product, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, product);
      expect(comp.productsCollection).toEqual(expectedCollection);
    });

    it('Should call unit query and add missing value', () => {
      const quatationDetails: IQuatationDetails = { id: 456 };
      const unit: IUnit = { id: 1126 };
      quatationDetails.unit = unit;

      const unitCollection: IUnit[] = [{ id: 17036 }];
      jest.spyOn(unitService, 'query').mockReturnValue(of(new HttpResponse({ body: unitCollection })));
      const expectedCollection: IUnit[] = [unit, ...unitCollection];
      jest.spyOn(unitService, 'addUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      expect(unitService.query).toHaveBeenCalled();
      expect(unitService.addUnitToCollectionIfMissing).toHaveBeenCalledWith(unitCollection, unit);
      expect(comp.unitsCollection).toEqual(expectedCollection);
    });

    it('Should call categories query and add missing value', () => {
      const quatationDetails: IQuatationDetails = { id: 456 };
      const categories: ICategories = { id: 14487 };
      quatationDetails.categories = categories;

      const categoriesCollection: ICategories[] = [{ id: 91388 }];
      jest.spyOn(categoriesService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriesCollection })));
      const expectedCollection: ICategories[] = [categories, ...categoriesCollection];
      jest.spyOn(categoriesService, 'addCategoriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      expect(categoriesService.query).toHaveBeenCalled();
      expect(categoriesService.addCategoriesToCollectionIfMissing).toHaveBeenCalledWith(categoriesCollection, categories);
      expect(comp.categoriesCollection).toEqual(expectedCollection);
    });

    it('Should call productQuatation query and add missing value', () => {
      const quatationDetails: IQuatationDetails = { id: 456 };
      const productQuatation: IProductQuatation = { id: 99560 };
      quatationDetails.productQuatation = productQuatation;

      const productQuatationCollection: IProductQuatation[] = [{ id: 14225 }];
      jest.spyOn(productQuatationService, 'query').mockReturnValue(of(new HttpResponse({ body: productQuatationCollection })));
      const expectedCollection: IProductQuatation[] = [productQuatation, ...productQuatationCollection];
      jest.spyOn(productQuatationService, 'addProductQuatationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      expect(productQuatationService.query).toHaveBeenCalled();
      expect(productQuatationService.addProductQuatationToCollectionIfMissing).toHaveBeenCalledWith(
        productQuatationCollection,
        productQuatation
      );
      expect(comp.productQuatationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const quatationDetails: IQuatationDetails = { id: 456 };
      const product: IProduct = { id: 40889 };
      quatationDetails.product = product;
      const unit: IUnit = { id: 31666 };
      quatationDetails.unit = unit;
      const categories: ICategories = { id: 99822 };
      quatationDetails.categories = categories;
      const productQuatation: IProductQuatation = { id: 17005 };
      quatationDetails.productQuatation = productQuatation;

      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(quatationDetails));
      expect(comp.productsCollection).toContain(product);
      expect(comp.unitsCollection).toContain(unit);
      expect(comp.categoriesCollection).toContain(categories);
      expect(comp.productQuatationsCollection).toContain(productQuatation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuatationDetails>>();
      const quatationDetails = { id: 123 };
      jest.spyOn(quatationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: quatationDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(quatationDetailsService.update).toHaveBeenCalledWith(quatationDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuatationDetails>>();
      const quatationDetails = new QuatationDetails();
      jest.spyOn(quatationDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: quatationDetails }));
      saveSubject.complete();

      // THEN
      expect(quatationDetailsService.create).toHaveBeenCalledWith(quatationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuatationDetails>>();
      const quatationDetails = { id: 123 };
      jest.spyOn(quatationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quatationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(quatationDetailsService.update).toHaveBeenCalledWith(quatationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
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

    describe('trackCategoriesById', () => {
      it('Should return tracked Categories primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoriesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProductQuatationById', () => {
      it('Should return tracked ProductQuatation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductQuatationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
