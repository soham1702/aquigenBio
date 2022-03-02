import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRawMaterialOrder } from '../raw-material-order.model';
import { RawMaterialOrderService } from '../service/raw-material-order.service';

@Component({
  templateUrl: './raw-material-order-delete-dialog.component.html',
})
export class RawMaterialOrderDeleteDialogComponent {
  rawMaterialOrder?: IRawMaterialOrder;

  constructor(protected rawMaterialOrderService: RawMaterialOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rawMaterialOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
