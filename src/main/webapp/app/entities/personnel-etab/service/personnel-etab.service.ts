import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonnelEtab, getPersonnelEtabIdentifier } from '../personnel-etab.model';

export type EntityResponseType = HttpResponse<IPersonnelEtab>;
export type EntityArrayResponseType = HttpResponse<IPersonnelEtab[]>;

@Injectable({ providedIn: 'root' })
export class PersonnelEtabService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/personnel-etabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personnelEtab: IPersonnelEtab): Observable<EntityResponseType> {
    return this.http.post<IPersonnelEtab>(this.resourceUrl, personnelEtab, { observe: 'response' });
  }

  update(personnelEtab: IPersonnelEtab): Observable<EntityResponseType> {
    return this.http.put<IPersonnelEtab>(`${this.resourceUrl}/${getPersonnelEtabIdentifier(personnelEtab) as number}`, personnelEtab, {
      observe: 'response',
    });
  }

  partialUpdate(personnelEtab: IPersonnelEtab): Observable<EntityResponseType> {
    return this.http.patch<IPersonnelEtab>(`${this.resourceUrl}/${getPersonnelEtabIdentifier(personnelEtab) as number}`, personnelEtab, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonnelEtab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonnelEtab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPersonnelEtabToCollectionIfMissing(
    personnelEtabCollection: IPersonnelEtab[],
    ...personnelEtabsToCheck: (IPersonnelEtab | null | undefined)[]
  ): IPersonnelEtab[] {
    const personnelEtabs: IPersonnelEtab[] = personnelEtabsToCheck.filter(isPresent);
    if (personnelEtabs.length > 0) {
      const personnelEtabCollectionIdentifiers = personnelEtabCollection.map(
        personnelEtabItem => getPersonnelEtabIdentifier(personnelEtabItem)!
      );
      const personnelEtabsToAdd = personnelEtabs.filter(personnelEtabItem => {
        const personnelEtabIdentifier = getPersonnelEtabIdentifier(personnelEtabItem);
        if (personnelEtabIdentifier == null || personnelEtabCollectionIdentifiers.includes(personnelEtabIdentifier)) {
          return false;
        }
        personnelEtabCollectionIdentifiers.push(personnelEtabIdentifier);
        return true;
      });
      return [...personnelEtabsToAdd, ...personnelEtabCollection];
    }
    return personnelEtabCollection;
  }
}
