import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductQuatation } from '../product-quatation.model';
import { ProductQuatationService } from '../service/product-quatation.service';

@Component({
  templateUrl: './product-quatation-delete-dialog.component.html',
})
export class ProductQuatationDeleteDialogComponent {
  productQuatation?: IProductQuatation;

  constructor(protected productQuatationService: ProductQuatationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productQuatationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
