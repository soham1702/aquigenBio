import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUnit, Unit } from '../unit.model';
import { UnitService } from '../service/unit.service';

@Component({
  selector: 'jhi-unit-update',
  templateUrl: './unit-update.component.html',
})
export class UnitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    unitName: [],
    shortName: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
  });

  constructor(protected unitService: UnitService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unit }) => {
      if (unit.id === undefined) {
        const today = dayjs().startOf('day');
        unit.lastModified = today;
      }

      this.updateForm(unit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unit = this.createFromForm();
    if (unit.id !== undefined) {
      this.subscribeToSaveResponse(this.unitService.update(unit));
    } else {
      this.subscribeToSaveResponse(this.unitService.create(unit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnit>>): void {
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

  protected updateForm(unit: IUnit): void {
    this.editForm.patchValue({
      id: unit.id,
      unitName: unit.unitName,
      shortName: unit.shortName,
      freeField1: unit.freeField1,
      freeField2: unit.freeField2,
      lastModified: unit.lastModified ? unit.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: unit.lastModifiedBy,
      isDeleted: unit.isDeleted,
      isActive: unit.isActive,
    });
  }

  protected createFromForm(): IUnit {
    return {
      ...new Unit(),
      id: this.editForm.get(['id'])!.value,
      unitName: this.editForm.get(['unitName'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
    };
  }
}
