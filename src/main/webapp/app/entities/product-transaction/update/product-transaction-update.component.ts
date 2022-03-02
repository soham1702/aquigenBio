import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProductTransaction, ProductTransaction } from '../product-transaction.model';
import { ProductTransactionService } from '../service/product-transaction.service';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';

@Component({
  selector: 'jhi-product-transaction-update',
  templateUrl: './product-transaction-update.component.html',
})
export class ProductTransactionUpdateComponent implements OnInit {
  isSaving = false;

  warehousesCollection: IWarehouse[] = [];
  productsSharedCollection: IProduct[] = [];
  productInventoriesSharedCollection: IProductInventory[] = [];

  editForm = this.fb.group({
    id: [],
    qtySold: [],
    pricePerUnit: [],
    lotNumber: [],
    expirydate: [],
    totalAmount: [],
    gstAmount: [],
    description: [],
    lastModified: [],
    lastModifiedBy: [],
    warehouse: [],
    products: [],
    productInventory: [],
  });

  constructor(
    protected productTransactionService: ProductTransactionService,
    protected warehouseService: WarehouseService,
    protected productService: ProductService,
    protected productInventoryService: ProductInventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransaction }) => {
      if (productTransaction.id === undefined) {
        const today = dayjs().startOf('day');
        productTransaction.expirydate = today;
        productTransaction.lastModified = today;
      }

      this.updateForm(productTransaction);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productTransaction = this.createFromForm();
    if (productTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.productTransactionService.update(productTransaction));
    } else {
      this.subscribeToSaveResponse(this.productTransactionService.create(productTransaction));
    }
  }

  trackWarehouseById(index: number, item: IWarehouse): number {
    return item.id!;
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackProductInventoryById(index: number, item: IProductInventory): number {
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTransaction>>): void {
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

  protected updateForm(productTransaction: IProductTransaction): void {
    this.editForm.patchValue({
      id: productTransaction.id,
      qtySold: productTransaction.qtySold,
      pricePerUnit: productTransaction.pricePerUnit,
      lotNumber: productTransaction.lotNumber,
      expirydate: productTransaction.expirydate ? productTransaction.expirydate.format(DATE_TIME_FORMAT) : null,
      totalAmount: productTransaction.totalAmount,
      gstAmount: productTransaction.gstAmount,
      description: productTransaction.description,
      lastModified: productTransaction.lastModified ? productTransaction.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: productTransaction.lastModifiedBy,
      warehouse: productTransaction.warehouse,
      products: productTransaction.products,
      productInventory: productTransaction.productInventory,
    });

    this.warehousesCollection = this.warehouseService.addWarehouseToCollectionIfMissing(
      this.warehousesCollection,
      productTransaction.warehouse
    );
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      ...(productTransaction.products ?? [])
    );
    this.productInventoriesSharedCollection = this.productInventoryService.addProductInventoryToCollectionIfMissing(
      this.productInventoriesSharedCollection,
      productTransaction.productInventory
    );
  }

  protected loadRelationshipsOptions(): void {
    this.warehouseService
      .query({ 'productTransactionId.specified': 'false' })
      .pipe(map((res: HttpResponse<IWarehouse[]>) => res.body ?? []))
      .pipe(
        map((warehouses: IWarehouse[]) =>
          this.warehouseService.addWarehouseToCollectionIfMissing(warehouses, this.editForm.get('warehouse')!.value)
        )
      )
      .subscribe((warehouses: IWarehouse[]) => (this.warehousesCollection = warehouses));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing(products, ...(this.editForm.get('products')!.value ?? []))
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.productInventoryService
      .query()
      .pipe(map((res: HttpResponse<IProductInventory[]>) => res.body ?? []))
      .pipe(
        map((productInventories: IProductInventory[]) =>
          this.productInventoryService.addProductInventoryToCollectionIfMissing(
            productInventories,
            this.editForm.get('productInventory')!.value
          )
        )
      )
      .subscribe((productInventories: IProductInventory[]) => (this.productInventoriesSharedCollection = productInventories));
  }

  protected createFromForm(): IProductTransaction {
    return {
      ...new ProductTransaction(),
      id: this.editForm.get(['id'])!.value,
      qtySold: this.editForm.get(['qtySold'])!.value,
      pricePerUnit: this.editForm.get(['pricePerUnit'])!.value,
      lotNumber: this.editForm.get(['lotNumber'])!.value,
      expirydate: this.editForm.get(['expirydate'])!.value ? dayjs(this.editForm.get(['expirydate'])!.value, DATE_TIME_FORMAT) : undefined,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      gstAmount: this.editForm.get(['gstAmount'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      warehouse: this.editForm.get(['warehouse'])!.value,
      products: this.editForm.get(['products'])!.value,
      productInventory: this.editForm.get(['productInventory'])!.value,
    };
  }
}
