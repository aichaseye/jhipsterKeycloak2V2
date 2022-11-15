import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonnelEtab, PersonnelEtab } from '../personnel-etab.model';
import { PersonnelEtabService } from '../service/personnel-etab.service';

@Injectable({ providedIn: 'root' })
export class PersonnelEtabRoutingResolveService implements Resolve<IPersonnelEtab> {
  constructor(protected service: PersonnelEtabService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonnelEtab> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personnelEtab: HttpResponse<PersonnelEtab>) => {
          if (personnelEtab.body) {
            return of(personnelEtab.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonnelEtab());
  }
}
