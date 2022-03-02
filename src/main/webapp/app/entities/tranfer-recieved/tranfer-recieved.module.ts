import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TranferRecievedComponent } from './list/tranfer-recieved.component';
import { TranferRecievedDetailComponent } from './detail/tranfer-recieved-detail.component';
import { TranferRecievedUpdateComponent } from './update/tranfer-recieved-update.component';
import { TranferRecievedDeleteDialogComponent } from './delete/tranfer-recieved-delete-dialog.component';
import { TranferRecievedRoutingModule } from './route/tranfer-recieved-routing.module';

@NgModule({
  imports: [SharedModule, TranferRecievedRoutingModule],
  declarations: [
    TranferRecievedComponent,
    TranferRecievedDetailComponent,
    TranferRecievedUpdateComponent,
    TranferRecievedDeleteDialogComponent,
  ],
  entryComponents: [TranferRecievedDeleteDialogComponent],
})
export class TranferRecievedModule {}
