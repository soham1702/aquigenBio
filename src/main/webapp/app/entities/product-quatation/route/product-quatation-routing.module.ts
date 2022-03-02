import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductQuatationComponent } from '../list/product-quatation.component';
import { ProductQuatationDetailComponent } from '../detail/product-quatation-detail.component';
import { ProductQuatationUpdateComponent } from '../update/product-quatation-update.component';
import { ProductQuatationRoutingResolveService } from './product-quatation-routing-resolve.service';

const productQuatationRoute: Routes = [
  {
    path: '',
    component: ProductQuatationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductQuatationDetailComponent,
    resolve: {
      productQuatation: ProductQuatationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductQuatationUpdateComponent,
    resolve: {
      productQuatation: ProductQuatationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductQuatationUpdateComponent,
    resolve: {
      productQuatation: ProductQuatationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productQuatationRoute)],
  exports: [RouterModule],
})
export class ProductQuatationRoutingModule {}
