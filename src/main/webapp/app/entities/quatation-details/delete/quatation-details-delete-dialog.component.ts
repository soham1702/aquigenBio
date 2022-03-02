import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuatationDetails } from '../quatation-details.model';
import { QuatationDetailsService } from '../service/quatation-details.service';

@Component({
  templateUrl: './quatation-details-delete-dialog.component.html',
})
export class QuatationDetailsDeleteDialogComponent {
  quatationDetails?: IQuatationDetails;

  constructor(protected quatationDetailsService: QuatationDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quatationDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
