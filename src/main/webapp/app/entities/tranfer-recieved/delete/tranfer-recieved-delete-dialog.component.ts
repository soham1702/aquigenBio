import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITranferRecieved } from '../tranfer-recieved.model';
import { TranferRecievedService } from '../service/tranfer-recieved.service';

@Component({
  templateUrl: './tranfer-recieved-delete-dialog.component.html',
})
export class TranferRecievedDeleteDialogComponent {
  tranferRecieved?: ITranferRecieved;

  constructor(protected tranferRecievedService: TranferRecievedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tranferRecievedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
