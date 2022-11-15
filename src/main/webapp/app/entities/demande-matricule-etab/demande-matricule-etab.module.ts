import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeMatriculeEtabComponent } from './list/demande-matricule-etab.component';
import { DemandeMatriculeEtabDetailComponent } from './detail/demande-matricule-etab-detail.component';
import { DemandeMatriculeEtabUpdateComponent } from './update/demande-matricule-etab-update.component';
import { DemandeMatriculeEtabDeleteDialogComponent } from './delete/demande-matricule-etab-delete-dialog.component';
import { DemandeMatriculeEtabRoutingModule } from './route/demande-matricule-etab-routing.module';

@NgModule({
  imports: [SharedModule, DemandeMatriculeEtabRoutingModule],
  declarations: [
    DemandeMatriculeEtabComponent,
    DemandeMatriculeEtabDetailComponent,
    DemandeMatriculeEtabUpdateComponent,
    DemandeMatriculeEtabDeleteDialogComponent,
  ],
  entryComponents: [DemandeMatriculeEtabDeleteDialogComponent],
})
export class DemandeMatriculeEtabModule {}
