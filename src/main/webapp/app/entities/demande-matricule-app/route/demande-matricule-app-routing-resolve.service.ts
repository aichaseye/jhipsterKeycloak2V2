import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeMatriculeApp, DemandeMatriculeApp } from '../demande-matricule-app.model';
import { DemandeMatriculeAppService } from '../service/demande-matricule-app.service';

@Injectable({ providedIn: 'root' })
export class DemandeMatriculeAppRoutingResolveService implements Resolve<IDemandeMatriculeApp> {
  constructor(protected service: DemandeMatriculeAppService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeMatriculeApp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeMatriculeApp: HttpResponse<DemandeMatriculeApp>) => {
          if (demandeMatriculeApp.body) {
            return of(demandeMatriculeApp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeMatriculeApp());
  }
}
