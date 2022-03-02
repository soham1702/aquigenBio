import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TranferDetailsApprovalsComponent } from '../list/tranfer-details-approvals.component';
import { TranferDetailsApprovalsDetailComponent } from '../detail/tranfer-details-approvals-detail.component';
import { TranferDetailsApprovalsUpdateComponent } from '../update/tranfer-details-approvals-update.component';
import { TranferDetailsApprovalsRoutingResolveService } from './tranfer-details-approvals-routing-resolve.service';

const tranferDetailsApprovalsRoute: Routes = [
  {
    path: '',
    component: TranferDetailsApprovalsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TranferDetailsApprovalsDetailComponent,
    resolve: {
      tranferDetailsApprovals: TranferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TranferDetailsApprovalsUpdateComponent,
    resolve: {
      tranferDetailsApprovals: TranferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TranferDetailsApprovalsUpdateComponent,
    resolve: {
      tranferDetailsApprovals: TranferDetailsApprovalsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tranferDetailsApprovalsRoute)],
  exports: [RouterModule],
})
export class TranferDetailsApprovalsRoutingModule {}
