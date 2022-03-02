import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RawMaterialOrderComponent } from '../list/raw-material-order.component';
import { RawMaterialOrderDetailComponent } from '../detail/raw-material-order-detail.component';
import { RawMaterialOrderUpdateComponent } from '../update/raw-material-order-update.component';
import { RawMaterialOrderRoutingResolveService } from './raw-material-order-routing-resolve.service';

const rawMaterialOrderRoute: Routes = [
  {
    path: '',
    component: RawMaterialOrderComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RawMaterialOrderDetailComponent,
    resolve: {
      rawMaterialOrder: RawMaterialOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RawMaterialOrderUpdateComponent,
    resolve: {
      rawMaterialOrder: RawMaterialOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RawMaterialOrderUpdateComponent,
    resolve: {
      rawMaterialOrder: RawMaterialOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rawMaterialOrderRoute)],
  exports: [RouterModule],
})
export class RawMaterialOrderRoutingModule {}
