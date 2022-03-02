import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProductInventory, ProductInventory } from '../product-inventory.model';
import { ProductInventoryService } from '../service/product-inventory.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

@Component({
  selector: 'jhi-product-inventory-update',
  templateUrl: './product-inventory-update.component.html',
})
export class ProductInventoryUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  warehousesSharedCollection: IWarehouse[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    inwardOutwardDate: [],
    inwardQty: [],
    outwardQty: [],
    totalQuanity: [],
    pricePerUnit: [],
    lotNo: [],
    expiryDate: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
    products: [],
    warehouses: [],
    securityUsers: [],
  });

  constructor(
    protected productInventoryService: ProductInventoryService,
    protected productService: ProductService,
    protected warehouseService: WarehouseService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productInventory }) => {
      if (productInventory.id === undefined) {
        const today = dayjs().startOf('day');
        productInventory.inwardOutwardDate = today;
        productInventory.expiryDate = today;
        productInventory.lastModified = today;
      }

      this.updateForm(productInventory);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productInventory = this.createFromForm();
    if (productInventory.id !== undefined) {
      this.subscribeToSaveResponse(this.productInventoryService.update(productInventory));
    } else {
      this.subscribeToSaveResponse(this.productInventoryService.create(productInventory));
    }
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackWarehouseById(index: number, item: IWarehouse): number {
    return item.id!;
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  getSelectedProduct(option: IProduct, selectedVals?: IProduct[]): IProduct {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedWarehouse(option: IWarehouse, selectedVals?: IWarehouse[]): IWarehouse {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedSecurityUser(option: ISecurityUser, selectedVals?: ISecurityUser[]): ISecurityUser {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductInventory>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productInventory: IProductInventory): void {
    this.editForm.patchValue({
      id: productInventory.id,
      inwardOutwardDate: productInventory.inwardOutwardDate ? productInventory.inwardOutwardDate.format(DATE_TIME_FORMAT) : null,
      inwardQty: productInventory.inwardQty,
      outwardQty: productInventory.outwardQty,
      totalQuanity: productInventory.totalQuanity,
      pricePerUnit: productInventory.pricePerUnit,
      lotNo: productInventory.lotNo,
      expiryDate: productInventory.expiryDate ? productInventory.expiryDate.format(DATE_TIME_FORMAT) : null,
      freeField1: productInventory.freeField1,
      freeField2: productInventory.freeField2,
      freeField3: productInventory.freeField3,
      freeField4: productInventory.freeField4,
      lastModified: productInventory.lastModified ? productInventory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: productInventory.lastModifiedBy,
      isDeleted: productInventory.isDeleted,
      isActive: productInventory.isActive,
      products: productInventory.products,
      warehouses: productInventory.warehouses,
      securityUsers: productInventory.securityUsers,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      ...(productInventory.products ?? [])
    );
    this.warehousesSharedCollection = this.warehouseService.addWarehouseToCollectionIfMissing(
      this.warehousesSharedCollection,
      ...(productInventory.warehouses ?? [])
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      ...(productInventory.securityUsers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing(products, ...(this.editForm.get('products')!.value ?? []))
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.warehouseService
      .query()
      .pipe(map((res: HttpResponse<IWarehouse[]>) => res.body ?? []))
      .pipe(
        map((warehouses: IWarehouse[]) =>
          this.warehouseService.addWarehouseToCollectionIfMissing(warehouses, ...(this.editForm.get('warehouses')!.value ?? []))
        )
      )
      .subscribe((warehouses: IWarehouse[]) => (this.warehousesSharedCollection = warehouses));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, ...(this.editForm.get('securityUsers')!.value ?? []))
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));
  }

  protected createFromForm(): IProductInventory {
    return {
      ...new ProductInventory(),
      id: this.editForm.get(['id'])!.value,
      inwardOutwardDate: this.editForm.get(['inwardOutwardDate'])!.value
        ? dayjs(this.editForm.get(['inwardOutwardDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      inwardQty: this.editForm.get(['inwardQty'])!.value,
      outwardQty: this.editForm.get(['outwardQty'])!.value,
      totalQuanity: this.editForm.get(['totalQuanity'])!.value,
      pricePerUnit: this.editForm.get(['pricePerUnit'])!.value,
      lotNo: this.editForm.get(['lotNo'])!.value,
      expiryDate: this.editForm.get(['expiryDate'])!.value ? dayjs(this.editForm.get(['expiryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      products: this.editForm.get(['products'])!.value,
      warehouses: this.editForm.get(['warehouses'])!.value,
      securityUsers: this.editForm.get(['securityUsers'])!.value,
    };
  }
}
