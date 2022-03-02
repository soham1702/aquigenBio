import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TranferRecievedComponent } from '../list/tranfer-recieved.component';
import { TranferRecievedDetailComponent } from '../detail/tranfer-recieved-detail.component';
import { TranferRecievedUpdateComponent } from '../update/tranfer-recieved-update.component';
import { TranferRecievedRoutingResolveService } from './tranfer-recieved-routing-resolve.service';

const tranferRecievedRoute: Routes = [
  {
    path: '',
    component: TranferRecievedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TranferRecievedDetailComponent,
    resolve: {
      tranferRecieved: TranferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TranferRecievedUpdateComponent,
    resolve: {
      tranferRecieved: TranferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TranferRecievedUpdateComponent,
    resolve: {
      tranferRecieved: TranferRecievedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tranferRecievedRoute)],
  exports: [RouterModule],
})
export class TranferRecievedRoutingModule {}
