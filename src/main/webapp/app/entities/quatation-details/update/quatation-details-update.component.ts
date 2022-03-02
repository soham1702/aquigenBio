import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IQuatationDetails, QuatationDetails } from '../quatation-details.model';
import { QuatationDetailsService } from '../service/quatation-details.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IProductQuatation } from 'app/entities/product-quatation/product-quatation.model';
import { ProductQuatationService } from 'app/entities/product-quatation/service/product-quatation.service';

@Component({
  selector: 'jhi-quatation-details-update',
  templateUrl: './quatation-details-update.component.html',
})
export class QuatationDetailsUpdateComponent implements OnInit {
  isSaving = false;

  productsCollection: IProduct[] = [];
  unitsCollection: IUnit[] = [];
  categoriesCollection: ICategories[] = [];
  productQuatationsCollection: IProductQuatation[] = [];

  editForm = this.fb.group({
    id: [],
    availabelStock: [],
    quantity: [],
    ratsPerUnit: [],
    totalprice: [],
    discount: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    lastModified: [],
    lastModifiedBy: [],
    product: [],
    unit: [],
    categories: [],
    productQuatation: [],
  });

  constructor(
    protected quatationDetailsService: QuatationDetailsService,
    protected productService: ProductService,
    protected unitService: UnitService,
    protected categoriesService: CategoriesService,
    protected productQuatationService: ProductQuatationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quatationDetails }) => {
      if (quatationDetails.id === undefined) {
        const today = dayjs().startOf('day');
        quatationDetails.lastModified = today;
      }

      this.updateForm(quatationDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quatationDetails = this.createFromForm();
    if (quatationDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.quatationDetailsService.update(quatationDetails));
    } else {
      this.subscribeToSaveResponse(this.quatationDetailsService.create(quatationDetails));
    }
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackUnitById(index: number, item: IUnit): number {
    return item.id!;
  }

  trackCategoriesById(index: number, item: ICategories): number {
    return item.id!;
  }

  trackProductQuatationById(index: number, item: IProductQuatation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuatationDetails>>): void {
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

  protected updateForm(quatationDetails: IQuatationDetails): void {
    this.editForm.patchValue({
      id: quatationDetails.id,
      availabelStock: quatationDetails.availabelStock,
      quantity: quatationDetails.quantity,
      ratsPerUnit: quatationDetails.ratsPerUnit,
      totalprice: quatationDetails.totalprice,
      discount: quatationDetails.discount,
      freeField1: quatationDetails.freeField1,
      freeField2: quatationDetails.freeField2,
      freeField3: quatationDetails.freeField3,
      freeField4: quatationDetails.freeField4,
      lastModified: quatationDetails.lastModified ? quatationDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: quatationDetails.lastModifiedBy,
      product: quatationDetails.product,
      unit: quatationDetails.unit,
      categories: quatationDetails.categories,
      productQuatation: quatationDetails.productQuatation,
    });

    this.productsCollection = this.productService.addProductToCollectionIfMissing(this.productsCollection, quatationDetails.product);
    this.unitsCollection = this.unitService.addUnitToCollectionIfMissing(this.unitsCollection, quatationDetails.unit);
    this.categoriesCollection = this.categoriesService.addCategoriesToCollectionIfMissing(
      this.categoriesCollection,
      quatationDetails.categories
    );
    this.productQuatationsCollection = this.productQuatationService.addProductQuatationToCollectionIfMissing(
      this.productQuatationsCollection,
      quatationDetails.productQuatation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query({ 'quatationDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('product')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsCollection = products));

    this.unitService
      .query({ 'quatationDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUnit[]>) => res.body ?? []))
      .pipe(map((units: IUnit[]) => this.unitService.addUnitToCollectionIfMissing(units, this.editForm.get('unit')!.value)))
      .subscribe((units: IUnit[]) => (this.unitsCollection = units));

    this.categoriesService
      .query({ 'quatationDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICategories[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategories[]) =>
          this.categoriesService.addCategoriesToCollectionIfMissing(categories, this.editForm.get('categories')!.value)
        )
      )
      .subscribe((categories: ICategories[]) => (this.categoriesCollection = categories));

    this.productQuatationService
      .query({ 'quatationDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProductQuatation[]>) => res.body ?? []))
      .pipe(
        map((productQuatations: IProductQuatation[]) =>
          this.productQuatationService.addProductQuatationToCollectionIfMissing(
            productQuatations,
            this.editForm.get('productQuatation')!.value
          )
        )
      )
      .subscribe((productQuatations: IProductQuatation[]) => (this.productQuatationsCollection = productQuatations));
  }

  protected createFromForm(): IQuatationDetails {
    return {
      ...new QuatationDetails(),
      id: this.editForm.get(['id'])!.value,
      availabelStock: this.editForm.get(['availabelStock'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      ratsPerUnit: this.editForm.get(['ratsPerUnit'])!.value,
      totalprice: this.editForm.get(['totalprice'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      product: this.editForm.get(['product'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      productQuatation: this.editForm.get(['productQuatation'])!.value,
    };
  }
}
