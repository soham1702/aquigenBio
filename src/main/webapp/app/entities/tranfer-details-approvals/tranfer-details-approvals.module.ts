import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TranferDetailsApprovalsComponent } from './list/tranfer-details-approvals.component';
import { TranferDetailsApprovalsDetailComponent } from './detail/tranfer-details-approvals-detail.component';
import { TranferDetailsApprovalsUpdateComponent } from './update/tranfer-details-approvals-update.component';
import { TranferDetailsApprovalsDeleteDialogComponent } from './delete/tranfer-details-approvals-delete-dialog.component';
import { TranferDetailsApprovalsRoutingModule } from './route/tranfer-details-approvals-routing.module';

@NgModule({
  imports: [SharedModule, TranferDetailsApprovalsRoutingModule],
  declarations: [
    TranferDetailsApprovalsComponent,
    TranferDetailsApprovalsDetailComponent,
    TranferDetailsApprovalsUpdateComponent,
    TranferDetailsApprovalsDeleteDialogComponent,
  ],
  entryComponents: [TranferDetailsApprovalsDeleteDialogComponent],
})
export class TranferDetailsApprovalsModule {}
