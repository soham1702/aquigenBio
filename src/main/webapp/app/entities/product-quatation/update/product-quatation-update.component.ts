import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProductQuatation, ProductQuatation } from '../product-quatation.model';
import { ProductQuatationService } from '../service/product-quatation.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

@Component({
  selector: 'jhi-product-quatation-update',
  templateUrl: './product-quatation-update.component.html',
})
export class ProductQuatationUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    quatationdate: [],
    totalAmt: [],
    gst: [],
    discount: [],
    expectedDelivery: [],
    deliveryAddress: [],
    quoValidity: [],
    clientName: [],
    clientMobile: [],
    clientEmail: [],
    termsAndCondition: [],
    notes: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
  });

  constructor(
    protected productQuatationService: ProductQuatationService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productQuatation }) => {
      if (productQuatation.id === undefined) {
        const today = dayjs().startOf('day');
        productQuatation.quatationdate = today;
        productQuatation.expectedDelivery = today;
        productQuatation.quoValidity = today;
        productQuatation.lastModified = today;
      }

      this.updateForm(productQuatation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productQuatation = this.createFromForm();
    if (productQuatation.id !== undefined) {
      this.subscribeToSaveResponse(this.productQuatationService.update(productQuatation));
    } else {
      this.subscribeToSaveResponse(this.productQuatationService.create(productQuatation));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductQuatation>>): void {
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

  protected updateForm(productQuatation: IProductQuatation): void {
    this.editForm.patchValue({
      id: productQuatation.id,
      quatationdate: productQuatation.quatationdate ? productQuatation.quatationdate.format(DATE_TIME_FORMAT) : null,
      totalAmt: productQuatation.totalAmt,
      gst: productQuatation.gst,
      discount: productQuatation.discount,
      expectedDelivery: productQuatation.expectedDelivery ? productQuatation.expectedDelivery.format(DATE_TIME_FORMAT) : null,
      deliveryAddress: productQuatation.deliveryAddress,
      quoValidity: productQuatation.quoValidity ? productQuatation.quoValidity.format(DATE_TIME_FORMAT) : null,
      clientName: productQuatation.clientName,
      clientMobile: productQuatation.clientMobile,
      clientEmail: productQuatation.clientEmail,
      termsAndCondition: productQuatation.termsAndCondition,
      notes: productQuatation.notes,
      freeField1: productQuatation.freeField1,
      freeField2: productQuatation.freeField2,
      freeField3: productQuatation.freeField3,
      freeField4: productQuatation.freeField4,
      lastModified: productQuatation.lastModified ? productQuatation.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: productQuatation.lastModifiedBy,
      securityUser: productQuatation.securityUser,
    });

    this.securityUsersCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersCollection,
      productQuatation.securityUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityUserService
      .query({ 'productQuatationId.specified': 'false' })
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersCollection = securityUsers));
  }

  protected createFromForm(): IProductQuatation {
    return {
      ...new ProductQuatation(),
      id: this.editForm.get(['id'])!.value,
      quatationdate: this.editForm.get(['quatationdate'])!.value
        ? dayjs(this.editForm.get(['quatationdate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      totalAmt: this.editForm.get(['totalAmt'])!.value,
      gst: this.editForm.get(['gst'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      expectedDelivery: this.editForm.get(['expectedDelivery'])!.value
        ? dayjs(this.editForm.get(['expectedDelivery'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deliveryAddress: this.editForm.get(['deliveryAddress'])!.value,
      quoValidity: this.editForm.get(['quoValidity'])!.value
        ? dayjs(this.editForm.get(['quoValidity'])!.value, DATE_TIME_FORMAT)
        : undefined,
      clientName: this.editForm.get(['clientName'])!.value,
      clientMobile: this.editForm.get(['clientMobile'])!.value,
      clientEmail: this.editForm.get(['clientEmail'])!.value,
      termsAndCondition: this.editForm.get(['termsAndCondition'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
