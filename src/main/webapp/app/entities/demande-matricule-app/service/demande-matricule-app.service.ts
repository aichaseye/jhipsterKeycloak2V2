import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeMatriculeApp, getDemandeMatriculeAppIdentifier } from '../demande-matricule-app.model';

export type EntityResponseType = HttpResponse<IDemandeMatriculeApp>;
export type EntityArrayResponseType = HttpResponse<IDemandeMatriculeApp[]>;

@Injectable({ providedIn: 'root' })
export class DemandeMatriculeAppService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-matricule-apps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeMatriculeApp: IDemandeMatriculeApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeMatriculeApp);
    return this.http
      .post<IDemandeMatriculeApp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandeMatriculeApp: IDemandeMatriculeApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeMatriculeApp);
    return this.http
      .put<IDemandeMatriculeApp>(`${this.resourceUrl}/${getDemandeMatriculeAppIdentifier(demandeMatriculeApp) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demandeMatriculeApp: IDemandeMatriculeApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeMatriculeApp);
    return this.http
      .patch<IDemandeMatriculeApp>(`${this.resourceUrl}/${getDemandeMatriculeAppIdentifier(demandeMatriculeApp) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandeMatriculeApp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandeMatriculeApp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeMatriculeAppToCollectionIfMissing(
    demandeMatriculeAppCollection: IDemandeMatriculeApp[],
    ...demandeMatriculeAppsToCheck: (IDemandeMatriculeApp | null | undefined)[]
  ): IDemandeMatriculeApp[] {
    const demandeMatriculeApps: IDemandeMatriculeApp[] = demandeMatriculeAppsToCheck.filter(isPresent);
    if (demandeMatriculeApps.length > 0) {
      const demandeMatriculeAppCollectionIdentifiers = demandeMatriculeAppCollection.map(
        demandeMatriculeAppItem => getDemandeMatriculeAppIdentifier(demandeMatriculeAppItem)!
      );
      const demandeMatriculeAppsToAdd = demandeMatriculeApps.filter(demandeMatriculeAppItem => {
        const demandeMatriculeAppIdentifier = getDemandeMatriculeAppIdentifier(demandeMatriculeAppItem);
        if (demandeMatriculeAppIdentifier == null || demandeMatriculeAppCollectionIdentifiers.includes(demandeMatriculeAppIdentifier)) {
          return false;
        }
        demandeMatriculeAppCollectionIdentifiers.push(demandeMatriculeAppIdentifier);
        return true;
      });
      return [...demandeMatriculeAppsToAdd, ...demandeMatriculeAppCollection];
    }
    return demandeMatriculeAppCollection;
  }

  protected convertDateFromClient(demandeMatriculeApp: IDemandeMatriculeApp): IDemandeMatriculeApp {
    return Object.assign({}, demandeMatriculeApp, {
      dateNaissance: demandeMatriculeApp.dateNaissance?.isValid() ? demandeMatriculeApp.dateNaissance.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? dayjs(res.body.dateNaissance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demandeMatriculeApp: IDemandeMatriculeApp) => {
        demandeMatriculeApp.dateNaissance = demandeMatriculeApp.dateNaissance ? dayjs(demandeMatriculeApp.dateNaissance) : undefined;
      });
    }
    return res;
  }
}
