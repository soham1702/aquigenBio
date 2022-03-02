import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductTransactionService } from '../service/product-transaction.service';
import { IProductTransaction, ProductTransaction } from '../product-transaction.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';

import { ProductTransactionUpdateComponent } from './product-transaction-update.component';

describe('ProductTransaction Management Update Component', () => {
  let comp: ProductTransactionUpdateComponent;
  let fixture: ComponentFixture<ProductTransactionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productTransactionService: ProductTransactionService;
  let warehouseService: WarehouseService;
  let productService: ProductService;
  let productInventoryService: ProductInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductTransactionUpdateComponent],
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
      .overrideTemplate(ProductTransactionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductTransactionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productTransactionService = TestBed.inject(ProductTransactionService);
    warehouseService = TestBed.inject(WarehouseService);
    productService = TestBed.inject(ProductService);
    productInventoryService = TestBed.inject(ProductInventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call warehouse query and add missing value', () => {
      const productTransaction: IProductTransaction = { id: 456 };
      const warehouse: IWarehouse = { id: 81938 };
      productTransaction.warehouse = warehouse;

      const warehouseCollection: IWarehouse[] = [{ id: 41318 }];
      jest.spyOn(warehouseService, 'query').mockReturnValue(of(new HttpResponse({ body: warehouseCollection })));
      const expectedCollection: IWarehouse[] = [warehouse, ...warehouseCollection];
      jest.spyOn(warehouseService, 'addWarehouseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(warehouseService.query).toHaveBeenCalled();
      expect(warehouseService.addWarehouseToCollectionIfMissing).toHaveBeenCalledWith(warehouseCollection, warehouse);
      expect(comp.warehousesCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const productTransaction: IProductTransaction = { id: 456 };
      const products: IProduct[] = [{ id: 54457 }];
      productTransaction.products = products;

      const productCollection: IProduct[] = [{ id: 99851 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [...products];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProductInventory query and add missing value', () => {
      const productTransaction: IProductTransaction = { id: 456 };
      const productInventory: IProductInventory = { id: 55396 };
      productTransaction.productInventory = productInventory;

      const productInventoryCollection: IProductInventory[] = [{ id: 82117 }];
      jest.spyOn(productInventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: productInventoryCollection })));
      const additionalProductInventories = [productInventory];
      const expectedCollection: IProductInventory[] = [...additionalProductInventories, ...productInventoryCollection];
      jest.spyOn(productInventoryService, 'addProductInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(productInventoryService.query).toHaveBeenCalled();
      expect(productInventoryService.addProductInventoryToCollectionIfMissing).toHaveBeenCalledWith(
        productInventoryCollection,
        ...additionalProductInventories
      );
      expect(comp.productInventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productTransaction: IProductTransaction = { id: 456 };
      const warehouse: IWarehouse = { id: 90830 };
      productTransaction.warehouse = warehouse;
      const products: IProduct = { id: 62796 };
      productTransaction.products = [products];
      const productInventory: IProductInventory = { id: 73848 };
      productTransaction.productInventory = productInventory;

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productTransaction));
      expect(comp.warehousesCollection).toContain(warehouse);
      expect(comp.productsSharedCollection).toContain(products);
      expect(comp.productInventoriesSharedCollection).toContain(productInventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductTransaction>>();
      const productTransaction = { id: 123 };
      jest.spyOn(productTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransaction }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productTransactionService.update).toHaveBeenCalledWith(productTransaction);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductTransaction>>();
      const productTransaction = new ProductTransaction();
      jest.spyOn(productTransactionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransaction }));
      saveSubject.complete();

      // THEN
      expect(productTransactionService.create).toHaveBeenCalledWith(productTransaction);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductTransaction>>();
      const productTransaction = { id: 123 };
      jest.spyOn(productTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productTransactionService.update).toHaveBeenCalledWith(productTransaction);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackWarehouseById', () => {
      it('Should return tracked Warehouse primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackWarehouseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProductInventoryById', () => {
      it('Should return tracked ProductInventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedProduct', () => {
      it('Should return option if no Product is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedProduct(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Product for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedProduct(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Product is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedProduct(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
