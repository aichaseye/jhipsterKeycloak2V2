import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeMatriculeEtabComponent } from '../list/demande-matricule-etab.component';
import { DemandeMatriculeEtabDetailComponent } from '../detail/demande-matricule-etab-detail.component';
import { DemandeMatriculeEtabUpdateComponent } from '../update/demande-matricule-etab-update.component';
import { DemandeMatriculeEtabRoutingResolveService } from './demande-matricule-etab-routing-resolve.service';

const demandeMatriculeEtabRoute: Routes = [
  {
    path: '',
    component: DemandeMatriculeEtabComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeMatriculeEtabDetailComponent,
    resolve: {
      demandeMatriculeEtab: DemandeMatriculeEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeMatriculeEtabUpdateComponent,
    resolve: {
      demandeMatriculeEtab: DemandeMatriculeEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeMatriculeEtabUpdateComponent,
    resolve: {
      demandeMatriculeEtab: DemandeMatriculeEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeMatriculeEtabRoute)],
  exports: [RouterModule],
})
export class DemandeMatriculeEtabRoutingModule {}
