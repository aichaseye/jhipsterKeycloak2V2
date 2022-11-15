import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeMatriculeAppComponent } from '../list/demande-matricule-app.component';
import { DemandeMatriculeAppDetailComponent } from '../detail/demande-matricule-app-detail.component';
import { DemandeMatriculeAppUpdateComponent } from '../update/demande-matricule-app-update.component';
import { DemandeMatriculeAppRoutingResolveService } from './demande-matricule-app-routing-resolve.service';

const demandeMatriculeAppRoute: Routes = [
  {
    path: '',
    component: DemandeMatriculeAppComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeMatriculeAppDetailComponent,
    resolve: {
      demandeMatriculeApp: DemandeMatriculeAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeMatriculeAppUpdateComponent,
    resolve: {
      demandeMatriculeApp: DemandeMatriculeAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeMatriculeAppUpdateComponent,
    resolve: {
      demandeMatriculeApp: DemandeMatriculeAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeMatriculeAppRoute)],
  exports: [RouterModule],
})
export class DemandeMatriculeAppRoutingModule {}
