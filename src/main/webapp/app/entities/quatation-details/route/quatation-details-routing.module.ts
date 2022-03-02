import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QuatationDetailsComponent } from '../list/quatation-details.component';
import { QuatationDetailsDetailComponent } from '../detail/quatation-details-detail.component';
import { QuatationDetailsUpdateComponent } from '../update/quatation-details-update.component';
import { QuatationDetailsRoutingResolveService } from './quatation-details-routing-resolve.service';

const quatationDetailsRoute: Routes = [
  {
    path: '',
    component: QuatationDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuatationDetailsDetailComponent,
    resolve: {
      quatationDetails: QuatationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuatationDetailsUpdateComponent,
    resolve: {
      quatationDetails: QuatationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuatationDetailsUpdateComponent,
    resolve: {
      quatationDetails: QuatationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(quatationDetailsRoute)],
  exports: [RouterModule],
})
export class QuatationDetailsRoutingModule {}
