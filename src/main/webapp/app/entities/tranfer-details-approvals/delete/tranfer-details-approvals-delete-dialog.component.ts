import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITranferDetailsApprovals } from '../tranfer-details-approvals.model';
import { TranferDetailsApprovalsService } from '../service/tranfer-details-approvals.service';

@Component({
  templateUrl: './tranfer-details-approvals-delete-dialog.component.html',
})
export class TranferDetailsApprovalsDeleteDialogComponent {
  tranferDetailsApprovals?: ITranferDetailsApprovals;

  constructor(protected tranferDetailsApprovalsService: TranferDetailsApprovalsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tranferDetailsApprovalsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
