import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'etablissement',
        data: { pageTitle: 'Etablissements' },
        loadChildren: () => import('./etablissement/etablissement.module').then(m => m.EtablissementModule),
      },
      {
        path: 'filiere-etude',
        data: { pageTitle: 'FiliereEtudes' },
        loadChildren: () => import('./filiere-etude/filiere-etude.module').then(m => m.FiliereEtudeModule),
      },
      {
        path: 'serie-etude',
        data: { pageTitle: 'SerieEtudes' },
        loadChildren: () => import('./serie-etude/serie-etude.module').then(m => m.SerieEtudeModule),
      },
      {
        path: 'apprenant',
        data: { pageTitle: 'Apprenants' },
        loadChildren: () => import('./apprenant/apprenant.module').then(m => m.ApprenantModule),
      },
      {
        path: 'niveau-etude',
        data: { pageTitle: 'NiveauEtudes' },
        loadChildren: () => import('./niveau-etude/niveau-etude.module').then(m => m.NiveauEtudeModule),
      },
      {
        path: 'demande-matricule-app',
        data: { pageTitle: 'DemandeMatriculeApps' },
        loadChildren: () => import('./demande-matricule-app/demande-matricule-app.module').then(m => m.DemandeMatriculeAppModule),
      },
      {
        path: 'demande-matricule-etab',
        data: { pageTitle: 'DemandeMatriculeEtabs' },
        loadChildren: () => import('./demande-matricule-etab/demande-matricule-etab.module').then(m => m.DemandeMatriculeEtabModule),
      },
      {
        path: 'etat-demande',
        data: { pageTitle: 'EtatDemandes' },
        loadChildren: () => import('./etat-demande/etat-demande.module').then(m => m.EtatDemandeModule),
      },
      {
        path: 'matiere',
        data: { pageTitle: 'Matieres' },
        loadChildren: () => import('./matiere/matiere.module').then(m => m.MatiereModule),
      },
      {
        path: 'personnel-etab',
        data: { pageTitle: 'PersonnelEtabs' },
        loadChildren: () => import('./personnel-etab/personnel-etab.module').then(m => m.PersonnelEtabModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
