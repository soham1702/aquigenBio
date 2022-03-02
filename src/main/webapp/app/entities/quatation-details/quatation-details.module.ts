import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QuatationDetailsComponent } from './list/quatation-details.component';
import { QuatationDetailsDetailComponent } from './detail/quatation-details-detail.component';
import { QuatationDetailsUpdateComponent } from './update/quatation-details-update.component';
import { QuatationDetailsDeleteDialogComponent } from './delete/quatation-details-delete-dialog.component';
import { QuatationDetailsRoutingModule } from './route/quatation-details-routing.module';

@NgModule({
  imports: [SharedModule, QuatationDetailsRoutingModule],
  declarations: [
    QuatationDetailsComponent,
    QuatationDetailsDetailComponent,
    QuatationDetailsUpdateComponent,
    QuatationDetailsDeleteDialogComponent,
  ],
  entryComponents: [QuatationDetailsDeleteDialogComponent],
})
export class QuatationDetailsModule {}
