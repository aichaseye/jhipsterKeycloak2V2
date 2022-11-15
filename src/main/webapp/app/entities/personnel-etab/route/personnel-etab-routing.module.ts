import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonnelEtabComponent } from '../list/personnel-etab.component';
import { PersonnelEtabDetailComponent } from '../detail/personnel-etab-detail.component';
import { PersonnelEtabUpdateComponent } from '../update/personnel-etab-update.component';
import { PersonnelEtabRoutingResolveService } from './personnel-etab-routing-resolve.service';

const personnelEtabRoute: Routes = [
  {
    path: '',
    component: PersonnelEtabComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonnelEtabDetailComponent,
    resolve: {
      personnelEtab: PersonnelEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonnelEtabUpdateComponent,
    resolve: {
      personnelEtab: PersonnelEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonnelEtabUpdateComponent,
    resolve: {
      personnelEtab: PersonnelEtabRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personnelEtabRoute)],
  exports: [RouterModule],
})
export class PersonnelEtabRoutingModule {}
