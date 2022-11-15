import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeMatriculeEtab, getDemandeMatriculeEtabIdentifier } from '../demande-matricule-etab.model';

export type EntityResponseType = HttpResponse<IDemandeMatriculeEtab>;
export type EntityArrayResponseType = HttpResponse<IDemandeMatriculeEtab[]>;

@Injectable({ providedIn: 'root' })
export class DemandeMatriculeEtabService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-matricule-etabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeMatriculeEtab: IDemandeMatriculeEtab): Observable<EntityResponseType> {
    return this.http.post<IDemandeMatriculeEtab>(this.resourceUrl, demandeMatriculeEtab, { observe: 'response' });
  }

  update(demandeMatriculeEtab: IDemandeMatriculeEtab): Observable<EntityResponseType> {
    return this.http.put<IDemandeMatriculeEtab>(
      `${this.resourceUrl}/${getDemandeMatriculeEtabIdentifier(demandeMatriculeEtab) as number}`,
      demandeMatriculeEtab,
      { observe: 'response' }
    );
  }

  partialUpdate(demandeMatriculeEtab: IDemandeMatriculeEtab): Observable<EntityResponseType> {
    return this.http.patch<IDemandeMatriculeEtab>(
      `${this.resourceUrl}/${getDemandeMatriculeEtabIdentifier(demandeMatriculeEtab) as number}`,
      demandeMatriculeEtab,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandeMatriculeEtab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeMatriculeEtab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeMatriculeEtabToCollectionIfMissing(
    demandeMatriculeEtabCollection: IDemandeMatriculeEtab[],
    ...demandeMatriculeEtabsToCheck: (IDemandeMatriculeEtab | null | undefined)[]
  ): IDemandeMatriculeEtab[] {
    const demandeMatriculeEtabs: IDemandeMatriculeEtab[] = demandeMatriculeEtabsToCheck.filter(isPresent);
    if (demandeMatriculeEtabs.length > 0) {
      const demandeMatriculeEtabCollectionIdentifiers = demandeMatriculeEtabCollection.map(
        demandeMatriculeEtabItem => getDemandeMatriculeEtabIdentifier(demandeMatriculeEtabItem)!
      );
      const demandeMatriculeEtabsToAdd = demandeMatriculeEtabs.filter(demandeMatriculeEtabItem => {
        const demandeMatriculeEtabIdentifier = getDemandeMatriculeEtabIdentifier(demandeMatriculeEtabItem);
        if (demandeMatriculeEtabIdentifier == null || demandeMatriculeEtabCollectionIdentifiers.includes(demandeMatriculeEtabIdentifier)) {
          return false;
        }
        demandeMatriculeEtabCollectionIdentifiers.push(demandeMatriculeEtabIdentifier);
        return true;
      });
      return [...demandeMatriculeEtabsToAdd, ...demandeMatriculeEtabCollection];
    }
    return demandeMatriculeEtabCollection;
  }
}
