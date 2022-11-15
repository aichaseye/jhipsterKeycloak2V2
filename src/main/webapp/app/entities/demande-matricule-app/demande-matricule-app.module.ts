import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeMatriculeAppComponent } from './list/demande-matricule-app.component';
import { DemandeMatriculeAppDetailComponent } from './detail/demande-matricule-app-detail.component';
import { DemandeMatriculeAppUpdateComponent } from './update/demande-matricule-app-update.component';
import { DemandeMatriculeAppDeleteDialogComponent } from './delete/demande-matricule-app-delete-dialog.component';
import { DemandeMatriculeAppRoutingModule } from './route/demande-matricule-app-routing.module';

@NgModule({
  imports: [SharedModule, DemandeMatriculeAppRoutingModule],
  declarations: [
    DemandeMatriculeAppComponent,
    DemandeMatriculeAppDetailComponent,
    DemandeMatriculeAppUpdateComponent,
    DemandeMatriculeAppDeleteDialogComponent,
  ],
  entryComponents: [DemandeMatriculeAppDeleteDialogComponent],
})
export class DemandeMatriculeAppModule {}
