import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtatDemande, getEtatDemandeIdentifier } from '../etat-demande.model';

export type EntityResponseType = HttpResponse<IEtatDemande>;
export type EntityArrayResponseType = HttpResponse<IEtatDemande[]>;

@Injectable({ providedIn: 'root' })
export class EtatDemandeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etat-demandes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatDemande);
    return this.http
      .post<IEtatDemande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatDemande);
    return this.http
      .put<IEtatDemande>(`${this.resourceUrl}/${getEtatDemandeIdentifier(etatDemande) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(etatDemande: IEtatDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etatDemande);
    return this.http
      .patch<IEtatDemande>(`${this.resourceUrl}/${getEtatDemandeIdentifier(etatDemande) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtatDemande>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatDemande[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtatDemandeToCollectionIfMissing(
    etatDemandeCollection: IEtatDemande[],
    ...etatDemandesToCheck: (IEtatDemande | null | undefined)[]
  ): IEtatDemande[] {
    const etatDemandes: IEtatDemande[] = etatDemandesToCheck.filter(isPresent);
    if (etatDemandes.length > 0) {
      const etatDemandeCollectionIdentifiers = etatDemandeCollection.map(etatDemandeItem => getEtatDemandeIdentifier(etatDemandeItem)!);
      const etatDemandesToAdd = etatDemandes.filter(etatDemandeItem => {
        const etatDemandeIdentifier = getEtatDemandeIdentifier(etatDemandeItem);
        if (etatDemandeIdentifier == null || etatDemandeCollectionIdentifiers.includes(etatDemandeIdentifier)) {
          return false;
        }
        etatDemandeCollectionIdentifiers.push(etatDemandeIdentifier);
        return true;
      });
      return [...etatDemandesToAdd, ...etatDemandeCollection];
    }
    return etatDemandeCollection;
  }

  protected convertDateFromClient(etatDemande: IEtatDemande): IEtatDemande {
    return Object.assign({}, etatDemande, {
      dateDisponibilite: etatDemande.dateDisponibilite?.isValid() ? etatDemande.dateDisponibilite.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDisponibilite = res.body.dateDisponibilite ? dayjs(res.body.dateDisponibilite) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((etatDemande: IEtatDemande) => {
        etatDemande.dateDisponibilite = etatDemande.dateDisponibilite ? dayjs(etatDemande.dateDisponibilite) : undefined;
      });
    }
    return res;
  }
}
