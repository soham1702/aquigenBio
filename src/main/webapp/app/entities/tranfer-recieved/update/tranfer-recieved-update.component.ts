import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';
import { TranferRecievedService } from '../service/tranfer-recieved.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

@Component({
  selector: 'jhi-tranfer-recieved-update',
  templateUrl: './tranfer-recieved-update.component.html',
})
export class TranferRecievedUpdateComponent implements OnInit {
  isSaving = false;

  transfersSharedCollection: ITransfer[] = [];

  editForm = this.fb.group({
    id: [],
    transferDate: [],
    qtyTransfered: [],
    qtyReceived: [],
    comment: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
    transfer: [],
  });

  constructor(
    protected tranferRecievedService: TranferRecievedService,
    protected transferService: TransferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tranferRecieved }) => {
      if (tranferRecieved.id === undefined) {
        const today = dayjs().startOf('day');
        tranferRecieved.transferDate = today;
        tranferRecieved.lastModified = today;
      }

      this.updateForm(tranferRecieved);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tranferRecieved = this.createFromForm();
    if (tranferRecieved.id !== undefined) {
      this.subscribeToSaveResponse(this.tranferRecievedService.update(tranferRecieved));
    } else {
      this.subscribeToSaveResponse(this.tranferRecievedService.create(tranferRecieved));
    }
  }

  trackTransferById(index: number, item: ITransfer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITranferRecieved>>): void {
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

  protected updateForm(tranferRecieved: ITranferRecieved): void {
    this.editForm.patchValue({
      id: tranferRecieved.id,
      transferDate: tranferRecieved.transferDate ? tranferRecieved.transferDate.format(DATE_TIME_FORMAT) : null,
      qtyTransfered: tranferRecieved.qtyTransfered,
      qtyReceived: tranferRecieved.qtyReceived,
      comment: tranferRecieved.comment,
      freeField1: tranferRecieved.freeField1,
      freeField2: tranferRecieved.freeField2,
      lastModified: tranferRecieved.lastModified ? tranferRecieved.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tranferRecieved.lastModifiedBy,
      isDeleted: tranferRecieved.isDeleted,
      isActive: tranferRecieved.isActive,
      transfer: tranferRecieved.transfer,
    });

    this.transfersSharedCollection = this.transferService.addTransferToCollectionIfMissing(
      this.transfersSharedCollection,
      tranferRecieved.transfer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.transferService
      .query()
      .pipe(map((res: HttpResponse<ITransfer[]>) => res.body ?? []))
      .pipe(
        map((transfers: ITransfer[]) =>
          this.transferService.addTransferToCollectionIfMissing(transfers, this.editForm.get('transfer')!.value)
        )
      )
      .subscribe((transfers: ITransfer[]) => (this.transfersSharedCollection = transfers));
  }

  protected createFromForm(): ITranferRecieved {
    return {
      ...new TranferRecieved(),
      id: this.editForm.get(['id'])!.value,
      transferDate: this.editForm.get(['transferDate'])!.value
        ? dayjs(this.editForm.get(['transferDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qtyTransfered: this.editForm.get(['qtyTransfered'])!.value,
      qtyReceived: this.editForm.get(['qtyReceived'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      transfer: this.editForm.get(['transfer'])!.value,
    };
  }
}
