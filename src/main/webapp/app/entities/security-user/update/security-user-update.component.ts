import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISecurityUser, SecurityUser } from '../security-user.model';
import { SecurityUserService } from '../service/security-user.service';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { ProductTransactionService } from 'app/entities/product-transaction/service/product-transaction.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';
import { IConsumptionDetails } from 'app/entities/consumption-details/consumption-details.model';
import { ConsumptionDetailsService } from 'app/entities/consumption-details/service/consumption-details.service';

@Component({
  selector: 'jhi-security-user-update',
  templateUrl: './security-user-update.component.html',
})
export class SecurityUserUpdateComponent implements OnInit {
  isSaving = false;

  productTransactionsCollection: IProductTransaction[] = [];
  securityPermissionsSharedCollection: ISecurityPermission[] = [];
  securityRolesSharedCollection: ISecurityRole[] = [];
  transfersSharedCollection: ITransfer[] = [];
  consumptionDetailsSharedCollection: IConsumptionDetails[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    designation: [],
    businessTitle: [],
    gSTDetails: [],
    login: [null, [Validators.required]],
    passwordHash: [null, [Validators.required]],
    email: [null, []],
    imageUrl: [],
    activated: [null, [Validators.required]],
    langKey: [],
    activationKey: [],
    resetKey: [],
    resetDate: [],
    mobileNo: [],
    oneTimePassword: [],
    otpExpiryTime: [],
    lastModified: [],
    lastModifiedBy: [],
    productTransaction: [],
    securityPermissions: [],
    securityRoles: [],
    transfer: [],
    consumptionDetails: [],
  });

  constructor(
    protected securityUserService: SecurityUserService,
    protected productTransactionService: ProductTransactionService,
    protected securityPermissionService: SecurityPermissionService,
    protected securityRoleService: SecurityRoleService,
    protected transferService: TransferService,
    protected consumptionDetailsService: ConsumptionDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      if (securityUser.id === undefined) {
        const today = dayjs().startOf('day');
        securityUser.resetDate = today;
        securityUser.otpExpiryTime = today;
        securityUser.lastModified = today;
      }

      this.updateForm(securityUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityUser = this.createFromForm();
    if (securityUser.id !== undefined) {
      this.subscribeToSaveResponse(this.securityUserService.update(securityUser));
    } else {
      this.subscribeToSaveResponse(this.securityUserService.create(securityUser));
    }
  }

  trackProductTransactionById(index: number, item: IProductTransaction): number {
    return item.id!;
  }

  trackSecurityPermissionById(index: number, item: ISecurityPermission): number {
    return item.id!;
  }

  trackSecurityRoleById(index: number, item: ISecurityRole): number {
    return item.id!;
  }

  trackTransferById(index: number, item: ITransfer): number {
    return item.id!;
  }

  trackConsumptionDetailsById(index: number, item: IConsumptionDetails): number {
    return item.id!;
  }

  getSelectedSecurityPermission(option: ISecurityPermission, selectedVals?: ISecurityPermission[]): ISecurityPermission {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedSecurityRole(option: ISecurityRole, selectedVals?: ISecurityRole[]): ISecurityRole {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityUser>>): void {
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

  protected updateForm(securityUser: ISecurityUser): void {
    this.editForm.patchValue({
      id: securityUser.id,
      firstName: securityUser.firstName,
      lastName: securityUser.lastName,
      designation: securityUser.designation,
      businessTitle: securityUser.businessTitle,
      gSTDetails: securityUser.gSTDetails,
      login: securityUser.login,
      passwordHash: securityUser.passwordHash,
      email: securityUser.email,
      imageUrl: securityUser.imageUrl,
      activated: securityUser.activated,
      langKey: securityUser.langKey,
      activationKey: securityUser.activationKey,
      resetKey: securityUser.resetKey,
      resetDate: securityUser.resetDate ? securityUser.resetDate.format(DATE_TIME_FORMAT) : null,
      mobileNo: securityUser.mobileNo,
      oneTimePassword: securityUser.oneTimePassword,
      otpExpiryTime: securityUser.otpExpiryTime ? securityUser.otpExpiryTime.format(DATE_TIME_FORMAT) : null,
      lastModified: securityUser.lastModified ? securityUser.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: securityUser.lastModifiedBy,
      productTransaction: securityUser.productTransaction,
      securityPermissions: securityUser.securityPermissions,
      securityRoles: securityUser.securityRoles,
      transfer: securityUser.transfer,
      consumptionDetails: securityUser.consumptionDetails,
    });

    this.productTransactionsCollection = this.productTransactionService.addProductTransactionToCollectionIfMissing(
      this.productTransactionsCollection,
      securityUser.productTransaction
    );
    this.securityPermissionsSharedCollection = this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
      this.securityPermissionsSharedCollection,
      ...(securityUser.securityPermissions ?? [])
    );
    this.securityRolesSharedCollection = this.securityRoleService.addSecurityRoleToCollectionIfMissing(
      this.securityRolesSharedCollection,
      ...(securityUser.securityRoles ?? [])
    );
    this.transfersSharedCollection = this.transferService.addTransferToCollectionIfMissing(
      this.transfersSharedCollection,
      securityUser.transfer
    );
    this.consumptionDetailsSharedCollection = this.consumptionDetailsService.addConsumptionDetailsToCollectionIfMissing(
      this.consumptionDetailsSharedCollection,
      securityUser.consumptionDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productTransactionService
      .query({ 'securityUserId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProductTransaction[]>) => res.body ?? []))
      .pipe(
        map((productTransactions: IProductTransaction[]) =>
          this.productTransactionService.addProductTransactionToCollectionIfMissing(
            productTransactions,
            this.editForm.get('productTransaction')!.value
          )
        )
      )
      .subscribe((productTransactions: IProductTransaction[]) => (this.productTransactionsCollection = productTransactions));

    this.securityPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityPermission[]>) => res.body ?? []))
      .pipe(
        map((securityPermissions: ISecurityPermission[]) =>
          this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
            securityPermissions,
            ...(this.editForm.get('securityPermissions')!.value ?? [])
          )
        )
      )
      .subscribe((securityPermissions: ISecurityPermission[]) => (this.securityPermissionsSharedCollection = securityPermissions));

    this.securityRoleService
      .query()
      .pipe(map((res: HttpResponse<ISecurityRole[]>) => res.body ?? []))
      .pipe(
        map((securityRoles: ISecurityRole[]) =>
          this.securityRoleService.addSecurityRoleToCollectionIfMissing(securityRoles, ...(this.editForm.get('securityRoles')!.value ?? []))
        )
      )
      .subscribe((securityRoles: ISecurityRole[]) => (this.securityRolesSharedCollection = securityRoles));

    this.transferService
      .query()
      .pipe(map((res: HttpResponse<ITransfer[]>) => res.body ?? []))
      .pipe(
        map((transfers: ITransfer[]) =>
          this.transferService.addTransferToCollectionIfMissing(transfers, this.editForm.get('transfer')!.value)
        )
      )
      .subscribe((transfers: ITransfer[]) => (this.transfersSharedCollection = transfers));

    this.consumptionDetailsService
      .query()
      .pipe(map((res: HttpResponse<IConsumptionDetails[]>) => res.body ?? []))
      .pipe(
        map((consumptionDetails: IConsumptionDetails[]) =>
          this.consumptionDetailsService.addConsumptionDetailsToCollectionIfMissing(
            consumptionDetails,
            this.editForm.get('consumptionDetails')!.value
          )
        )
      )
      .subscribe((consumptionDetails: IConsumptionDetails[]) => (this.consumptionDetailsSharedCollection = consumptionDetails));
  }

  protected createFromForm(): ISecurityUser {
    return {
      ...new SecurityUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      businessTitle: this.editForm.get(['businessTitle'])!.value,
      gSTDetails: this.editForm.get(['gSTDetails'])!.value,
      login: this.editForm.get(['login'])!.value,
      passwordHash: this.editForm.get(['passwordHash'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      langKey: this.editForm.get(['langKey'])!.value,
      activationKey: this.editForm.get(['activationKey'])!.value,
      resetKey: this.editForm.get(['resetKey'])!.value,
      resetDate: this.editForm.get(['resetDate'])!.value ? dayjs(this.editForm.get(['resetDate'])!.value, DATE_TIME_FORMAT) : undefined,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      oneTimePassword: this.editForm.get(['oneTimePassword'])!.value,
      otpExpiryTime: this.editForm.get(['otpExpiryTime'])!.value
        ? dayjs(this.editForm.get(['otpExpiryTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      productTransaction: this.editForm.get(['productTransaction'])!.value,
      securityPermissions: this.editForm.get(['securityPermissions'])!.value,
      securityRoles: this.editForm.get(['securityRoles'])!.value,
      transfer: this.editForm.get(['transfer'])!.value,
      consumptionDetails: this.editForm.get(['consumptionDetails'])!.value,
    };
  }
}
