import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INiveauEtude, getNiveauEtudeIdentifier } from '../niveau-etude.model';

export type EntityResponseType = HttpResponse<INiveauEtude>;
export type EntityArrayResponseType = HttpResponse<INiveauEtude[]>;

@Injectable({ providedIn: 'root' })
export class NiveauEtudeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/niveau-etudes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(niveauEtude: INiveauEtude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauEtude);
    return this.http
      .post<INiveauEtude>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(niveauEtude: INiveauEtude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauEtude);
    return this.http
      .put<INiveauEtude>(`${this.resourceUrl}/${getNiveauEtudeIdentifier(niveauEtude) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(niveauEtude: INiveauEtude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauEtude);
    return this.http
      .patch<INiveauEtude>(`${this.resourceUrl}/${getNiveauEtudeIdentifier(niveauEtude) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INiveauEtude>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INiveauEtude[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNiveauEtudeToCollectionIfMissing(
    niveauEtudeCollection: INiveauEtude[],
    ...niveauEtudesToCheck: (INiveauEtude | null | undefined)[]
  ): INiveauEtude[] {
    const niveauEtudes: INiveauEtude[] = niveauEtudesToCheck.filter(isPresent);
    if (niveauEtudes.length > 0) {
      const niveauEtudeCollectionIdentifiers = niveauEtudeCollection.map(niveauEtudeItem => getNiveauEtudeIdentifier(niveauEtudeItem)!);
      const niveauEtudesToAdd = niveauEtudes.filter(niveauEtudeItem => {
        const niveauEtudeIdentifier = getNiveauEtudeIdentifier(niveauEtudeItem);
        if (niveauEtudeIdentifier == null || niveauEtudeCollectionIdentifiers.includes(niveauEtudeIdentifier)) {
          return false;
        }
        niveauEtudeCollectionIdentifiers.push(niveauEtudeIdentifier);
        return true;
      });
      return [...niveauEtudesToAdd, ...niveauEtudeCollection];
    }
    return niveauEtudeCollection;
  }

  protected convertDateFromClient(niveauEtude: INiveauEtude): INiveauEtude {
    return Object.assign({}, niveauEtude, {
      anneeEtude: niveauEtude.anneeEtude?.isValid() ? niveauEtude.anneeEtude.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.anneeEtude = res.body.anneeEtude ? dayjs(res.body.anneeEtude) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((niveauEtude: INiveauEtude) => {
        niveauEtude.anneeEtude = niveauEtude.anneeEtude ? dayjs(niveauEtude.anneeEtude) : undefined;
      });
    }
    return res;
  }
}
