import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { IDemandeMatriculeApp, DemandeMatriculeApp } from '../demande-matricule-app.model';

import { DemandeMatriculeAppService } from './demande-matricule-app.service';

describe('DemandeMatriculeApp Service', () => {
  let service: DemandeMatriculeAppService;
  let httpMock: HttpTestingController;
  let elemDefault: IDemandeMatriculeApp;
  let expectedResult: IDemandeMatriculeApp | IDemandeMatriculeApp[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeMatriculeAppService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      numeroDemandeApp: 'AAAAAAA',
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      numIdNational: 0,
      sexe: Sexe.Masclin,
      email: 'AAAAAAA',
      dateNaissance: currentDate,
      lieuNaissance: 'AAAAAAA',
      adresse: 'AAAAAAA',
      etabFrequente: 'AAAAAAA',
      matriculeEtab: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateNaissance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DemandeMatriculeApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateNaissance: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
        },
        returnedFromService
      );

      service.create(new DemandeMatriculeApp()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeMatriculeApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroDemandeApp: 'BBBBBB',
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          numIdNational: 1,
          sexe: 'BBBBBB',
          email: 'BBBBBB',
          dateNaissance: currentDate.format(DATE_FORMAT),
          lieuNaissance: 'BBBBBB',
          adresse: 'BBBBBB',
          etabFrequente: 'BBBBBB',
          matriculeEtab: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeMatriculeApp', () => {
      const patchObject = Object.assign(
        {
          numIdNational: 1,
          email: 'BBBBBB',
          dateNaissance: currentDate.format(DATE_FORMAT),
        },
        new DemandeMatriculeApp()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeMatriculeApp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroDemandeApp: 'BBBBBB',
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          numIdNational: 1,
          sexe: 'BBBBBB',
          email: 'BBBBBB',
          dateNaissance: currentDate.format(DATE_FORMAT),
          lieuNaissance: 'BBBBBB',
          adresse: 'BBBBBB',
          etabFrequente: 'BBBBBB',
          matriculeEtab: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaissance: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DemandeMatriculeApp', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDemandeMatriculeAppToCollectionIfMissing', () => {
      it('should add a DemandeMatriculeApp to an empty array', () => {
        const demandeMatriculeApp: IDemandeMatriculeApp = { id: 123 };
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing([], demandeMatriculeApp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeMatriculeApp);
      });

      it('should not add a DemandeMatriculeApp to an array that contains it', () => {
        const demandeMatriculeApp: IDemandeMatriculeApp = { id: 123 };
        const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [
          {
            ...demandeMatriculeApp,
          },
          { id: 456 },
        ];
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing(demandeMatriculeAppCollection, demandeMatriculeApp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeMatriculeApp to an array that doesn't contain it", () => {
        const demandeMatriculeApp: IDemandeMatriculeApp = { id: 123 };
        const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [{ id: 456 }];
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing(demandeMatriculeAppCollection, demandeMatriculeApp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeMatriculeApp);
      });

      it('should add only unique DemandeMatriculeApp to an array', () => {
        const demandeMatriculeAppArray: IDemandeMatriculeApp[] = [{ id: 123 }, { id: 456 }, { id: 77538 }];
        const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [{ id: 123 }];
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing(demandeMatriculeAppCollection, ...demandeMatriculeAppArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeMatriculeApp: IDemandeMatriculeApp = { id: 123 };
        const demandeMatriculeApp2: IDemandeMatriculeApp = { id: 456 };
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing([], demandeMatriculeApp, demandeMatriculeApp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeMatriculeApp);
        expect(expectedResult).toContain(demandeMatriculeApp2);
      });

      it('should accept null and undefined values', () => {
        const demandeMatriculeApp: IDemandeMatriculeApp = { id: 123 };
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing([], null, demandeMatriculeApp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeMatriculeApp);
      });

      it('should return initial array if no DemandeMatriculeApp is added', () => {
        const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [{ id: 123 }];
        expectedResult = service.addDemandeMatriculeAppToCollectionIfMissing(demandeMatriculeAppCollection, undefined, null);
        expect(expectedResult).toEqual(demandeMatriculeAppCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
