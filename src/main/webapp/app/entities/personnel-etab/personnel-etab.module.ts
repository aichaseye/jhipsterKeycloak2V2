import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonnelEtabComponent } from './list/personnel-etab.component';
import { PersonnelEtabDetailComponent } from './detail/personnel-etab-detail.component';
import { PersonnelEtabUpdateComponent } from './update/personnel-etab-update.component';
import { PersonnelEtabDeleteDialogComponent } from './delete/personnel-etab-delete-dialog.component';
import { PersonnelEtabRoutingModule } from './route/personnel-etab-routing.module';

@NgModule({
  imports: [SharedModule, PersonnelEtabRoutingModule],
  declarations: [PersonnelEtabComponent, PersonnelEtabDetailComponent, PersonnelEtabUpdateComponent, PersonnelEtabDeleteDialogComponent],
  entryComponents: [PersonnelEtabDeleteDialogComponent],
})
export class PersonnelEtabModule {}
