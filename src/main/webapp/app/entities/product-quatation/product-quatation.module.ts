import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductQuatationComponent } from './list/product-quatation.component';
import { ProductQuatationDetailComponent } from './detail/product-quatation-detail.component';
import { ProductQuatationUpdateComponent } from './update/product-quatation-update.component';
import { ProductQuatationDeleteDialogComponent } from './delete/product-quatation-delete-dialog.component';
import { ProductQuatationRoutingModule } from './route/product-quatation-routing.module';

@NgModule({
  imports: [SharedModule, ProductQuatationRoutingModule],
  declarations: [
    ProductQuatationComponent,
    ProductQuatationDetailComponent,
    ProductQuatationUpdateComponent,
    ProductQuatationDeleteDialogComponent,
  ],
  entryComponents: [ProductQuatationDeleteDialogComponent],
})
export class ProductQuatationModule {}
