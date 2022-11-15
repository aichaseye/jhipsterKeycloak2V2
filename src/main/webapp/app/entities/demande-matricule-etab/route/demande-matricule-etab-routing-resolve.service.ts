import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeMatriculeEtab, DemandeMatriculeEtab } from '../demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from '../service/demande-matricule-etab.service';

@Injectable({ providedIn: 'root' })
export class DemandeMatriculeEtabRoutingResolveService implements Resolve<IDemandeMatriculeEtab> {
  constructor(protected service: DemandeMatriculeEtabService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeMatriculeEtab> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeMatriculeEtab: HttpResponse<DemandeMatriculeEtab>) => {
          if (demandeMatriculeEtab.body) {
            return of(demandeMatriculeEtab.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandeMatriculeEtab());
  }
}
