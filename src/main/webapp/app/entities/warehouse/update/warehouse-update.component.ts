import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IWarehouse, Warehouse } from '../warehouse.model';
import { WarehouseService } from '../service/warehouse.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

@Component({
  selector: 'jhi-warehouse-update',
  templateUrl: './warehouse-update.component.html',
})
export class WarehouseUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    warehouseId: [],
    whName: [],
    address: [],
    pincode: [],
    city: [],
    state: [],
    country: [],
    gSTDetails: [],
    managerName: [],
    managerEmail: [],
    managerContact: [],
    contact: [],
    isDeleted: [],
    isActive: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityUsers: [],
  });

  constructor(
    protected warehouseService: WarehouseService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ warehouse }) => {
      if (warehouse.id === undefined) {
        const today = dayjs().startOf('day');
        warehouse.lastModified = today;
      }

      this.updateForm(warehouse);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const warehouse = this.createFromForm();
    if (warehouse.id !== undefined) {
      this.subscribeToSaveResponse(this.warehouseService.update(warehouse));
    } else {
      this.subscribeToSaveResponse(this.warehouseService.create(warehouse));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarehouse>>): void {
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

  protected updateForm(warehouse: IWarehouse): void {
    this.editForm.patchValue({
      id: warehouse.id,
      warehouseId: warehouse.warehouseId,
      whName: warehouse.whName,
      address: warehouse.address,
      pincode: warehouse.pincode,
      city: warehouse.city,
      state: warehouse.state,
      country: warehouse.country,
      gSTDetails: warehouse.gSTDetails,
      managerName: warehouse.managerName,
      managerEmail: warehouse.managerEmail,
      managerContact: warehouse.managerContact,
      contact: warehouse.contact,
      isDeleted: warehouse.isDeleted,
      isActive: warehouse.isActive,
      lastModified: warehouse.lastModified ? warehouse.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: warehouse.lastModifiedBy,
      securityUsers: warehouse.securityUsers,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      ...(warehouse.securityUsers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IWarehouse {
    return {
      ...new Warehouse(),
      id: this.editForm.get(['id'])!.value,
      warehouseId: this.editForm.get(['warehouseId'])!.value,
      whName: this.editForm.get(['whName'])!.value,
      address: this.editForm.get(['address'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      city: this.editForm.get(['city'])!.value,
      state: this.editForm.get(['state'])!.value,
      country: this.editForm.get(['country'])!.value,
      gSTDetails: this.editForm.get(['gSTDetails'])!.value,
      managerName: this.editForm.get(['managerName'])!.value,
      managerEmail: this.editForm.get(['managerEmail'])!.value,
      managerContact: this.editForm.get(['managerContact'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUsers: this.editForm.get(['securityUsers'])!.value,
    };
  }
}
