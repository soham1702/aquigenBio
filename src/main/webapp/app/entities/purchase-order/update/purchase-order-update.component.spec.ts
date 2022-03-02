import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PurchaseOrderService } from '../service/purchase-order.service';
import { IPurchaseOrder, PurchaseOrder } from '../purchase-order.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { RawMaterialOrderService } from 'app/entities/raw-material-order/service/raw-material-order.service';

import { PurchaseOrderUpdateComponent } from './purchase-order-update.component';

describe('PurchaseOrder Management Update Component', () => {
  let comp: PurchaseOrderUpdateComponent;
  let fixture: ComponentFixture<PurchaseOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let purchaseOrderService: PurchaseOrderService;
  let productInventoryService: ProductInventoryService;
  let warehouseService: WarehouseService;
  let securityUserService: SecurityUserService;
  let rawMaterialOrderService: RawMaterialOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PurchaseOrderUpdateComponent],
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
      .overrideTemplate(PurchaseOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PurchaseOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    purchaseOrderService = TestBed.inject(PurchaseOrderService);
    productInventoryService = TestBed.inject(ProductInventoryService);
    warehouseService = TestBed.inject(WarehouseService);
    securityUserService = TestBed.inject(SecurityUserService);
    rawMaterialOrderService = TestBed.inject(RawMaterialOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductInventory query and add missing value', () => {
      const purchaseOrder: IPurchaseOrder = { id: 456 };
      const productInventory: IProductInventory = { id: 8646 };
      purchaseOrder.productInventory = productInventory;

      const productInventoryCollection: IProductInventory[] = [{ id: 69318 }];
      jest.spyOn(productInventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: productInventoryCollection })));
      const additionalProductInventories = [productInventory];
      const expectedCollection: IProductInventory[] = [...additionalProductInventories, ...productInventoryCollection];
      jest.spyOn(productInventoryService, 'addProductInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      expect(productInventoryService.query).toHaveBeenCalled();
      expect(productInventoryService.addProductInventoryToCollectionIfMissing).toHaveBeenCalledWith(
        productInventoryCollection,
        ...additionalProductInventories
      );
      expect(comp.productInventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Warehouse query and add missing value', () => {
      const purchaseOrder: IPurchaseOrder = { id: 456 };
      const warehouse: IWarehouse = { id: 46315 };
      purchaseOrder.warehouse = warehouse;

      const warehouseCollection: IWarehouse[] = [{ id: 29047 }];
      jest.spyOn(warehouseService, 'query').mockReturnValue(of(new HttpResponse({ body: warehouseCollection })));
      const additionalWarehouses = [warehouse];
      const expectedCollection: IWarehouse[] = [...additionalWarehouses, ...warehouseCollection];
      jest.spyOn(warehouseService, 'addWarehouseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      expect(warehouseService.query).toHaveBeenCalled();
      expect(warehouseService.addWarehouseToCollectionIfMissing).toHaveBeenCalledWith(warehouseCollection, ...additionalWarehouses);
      expect(comp.warehousesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const purchaseOrder: IPurchaseOrder = { id: 456 };
      const securityUser: ISecurityUser = { id: 81316 };
      purchaseOrder.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 69279 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RawMaterialOrder query and add missing value', () => {
      const purchaseOrder: IPurchaseOrder = { id: 456 };
      const rawMaterialOrder: IRawMaterialOrder = { id: 41128 };
      purchaseOrder.rawMaterialOrder = rawMaterialOrder;

      const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 65344 }];
      jest.spyOn(rawMaterialOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: rawMaterialOrderCollection })));
      const additionalRawMaterialOrders = [rawMaterialOrder];
      const expectedCollection: IRawMaterialOrder[] = [...additionalRawMaterialOrders, ...rawMaterialOrderCollection];
      jest.spyOn(rawMaterialOrderService, 'addRawMaterialOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      expect(rawMaterialOrderService.query).toHaveBeenCalled();
      expect(rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing).toHaveBeenCalledWith(
        rawMaterialOrderCollection,
        ...additionalRawMaterialOrders
      );
      expect(comp.rawMaterialOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const purchaseOrder: IPurchaseOrder = { id: 456 };
      const productInventory: IProductInventory = { id: 27190 };
      purchaseOrder.productInventory = productInventory;
      const warehouse: IWarehouse = { id: 69636 };
      purchaseOrder.warehouse = warehouse;
      const securityUser: ISecurityUser = { id: 90967 };
      purchaseOrder.securityUser = securityUser;
      const rawMaterialOrder: IRawMaterialOrder = { id: 12217 };
      purchaseOrder.rawMaterialOrder = rawMaterialOrder;

      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(purchaseOrder));
      expect(comp.productInventoriesSharedCollection).toContain(productInventory);
      expect(comp.warehousesSharedCollection).toContain(warehouse);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.rawMaterialOrdersSharedCollection).toContain(rawMaterialOrder);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseOrder>>();
      const purchaseOrder = { id: 123 };
      jest.spyOn(purchaseOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseOrder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(purchaseOrderService.update).toHaveBeenCalledWith(purchaseOrder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseOrder>>();
      const purchaseOrder = new PurchaseOrder();
      jest.spyOn(purchaseOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseOrder }));
      saveSubject.complete();

      // THEN
      expect(purchaseOrderService.create).toHaveBeenCalledWith(purchaseOrder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseOrder>>();
      const purchaseOrder = { id: 123 };
      jest.spyOn(purchaseOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(purchaseOrderService.update).toHaveBeenCalledWith(purchaseOrder);
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

    describe('trackWarehouseById', () => {
      it('Should return tracked Warehouse primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackWarehouseById(0, entity);
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

    describe('trackRawMaterialOrderById', () => {
      it('Should return tracked RawMaterialOrder primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRawMaterialOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
