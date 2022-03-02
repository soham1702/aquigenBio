import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RawMaterialOrderComponent } from './list/raw-material-order.component';
import { RawMaterialOrderDetailComponent } from './detail/raw-material-order-detail.component';
import { RawMaterialOrderUpdateComponent } from './update/raw-material-order-update.component';
import { RawMaterialOrderDeleteDialogComponent } from './delete/raw-material-order-delete-dialog.component';
import { RawMaterialOrderRoutingModule } from './route/raw-material-order-routing.module';

@NgModule({
  imports: [SharedModule, RawMaterialOrderRoutingModule],
  declarations: [
    RawMaterialOrderComponent,
    RawMaterialOrderDetailComponent,
    RawMaterialOrderUpdateComponent,
    RawMaterialOrderDeleteDialogComponent,
  ],
  entryComponents: [RawMaterialOrderDeleteDialogComponent],
})
export class RawMaterialOrderModule {}
