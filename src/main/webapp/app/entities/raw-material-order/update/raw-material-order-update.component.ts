import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRawMaterialOrder, RawMaterialOrder } from '../raw-material-order.model';
import { RawMaterialOrderService } from '../service/raw-material-order.service';

@Component({
  selector: 'jhi-raw-material-order-update',
  templateUrl: './raw-material-order-update.component.html',
})
export class RawMaterialOrderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pricePerUnit: [],
    quantityUnit: [],
    quantity: [],
    deliveryDate: [],
    quantityCheck: [],
    orderedOn: [],
    orderStatus: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
  });

  constructor(
    protected rawMaterialOrderService: RawMaterialOrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterialOrder }) => {
      if (rawMaterialOrder.id === undefined) {
        const today = dayjs().startOf('day');
        rawMaterialOrder.deliveryDate = today;
        rawMaterialOrder.orderedOn = today;
        rawMaterialOrder.lastModified = today;
      }

      this.updateForm(rawMaterialOrder);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rawMaterialOrder = this.createFromForm();
    if (rawMaterialOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.rawMaterialOrderService.update(rawMaterialOrder));
    } else {
      this.subscribeToSaveResponse(this.rawMaterialOrderService.create(rawMaterialOrder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRawMaterialOrder>>): void {
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

  protected updateForm(rawMaterialOrder: IRawMaterialOrder): void {
    this.editForm.patchValue({
      id: rawMaterialOrder.id,
      pricePerUnit: rawMaterialOrder.pricePerUnit,
      quantityUnit: rawMaterialOrder.quantityUnit,
      quantity: rawMaterialOrder.quantity,
      deliveryDate: rawMaterialOrder.deliveryDate ? rawMaterialOrder.deliveryDate.format(DATE_TIME_FORMAT) : null,
      quantityCheck: rawMaterialOrder.quantityCheck,
      orderedOn: rawMaterialOrder.orderedOn ? rawMaterialOrder.orderedOn.format(DATE_TIME_FORMAT) : null,
      orderStatus: rawMaterialOrder.orderStatus,
      lastModified: rawMaterialOrder.lastModified ? rawMaterialOrder.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: rawMaterialOrder.lastModifiedBy,
      freeField1: rawMaterialOrder.freeField1,
      freeField2: rawMaterialOrder.freeField2,
      freeField3: rawMaterialOrder.freeField3,
      freeField4: rawMaterialOrder.freeField4,
    });
  }

  protected createFromForm(): IRawMaterialOrder {
    return {
      ...new RawMaterialOrder(),
      id: this.editForm.get(['id'])!.value,
      pricePerUnit: this.editForm.get(['pricePerUnit'])!.value,
      quantityUnit: this.editForm.get(['quantityUnit'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value
        ? dayjs(this.editForm.get(['deliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantityCheck: this.editForm.get(['quantityCheck'])!.value,
      orderedOn: this.editForm.get(['orderedOn'])!.value ? dayjs(this.editForm.get(['orderedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      orderStatus: this.editForm.get(['orderStatus'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
    };
  }
}
