import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';
import { IDemandeMatriculeEtab, DemandeMatriculeEtab } from '../demande-matricule-etab.model';

import { DemandeMatriculeEtabService } from './demande-matricule-etab.service';

describe('DemandeMatriculeEtab Service', () => {
  let service: DemandeMatriculeEtabService;
  let httpMock: HttpTestingController;
  let elemDefault: IDemandeMatriculeEtab;
  let expectedResult: IDemandeMatriculeEtab | IDemandeMatriculeEtab[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeMatriculeEtabService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      numeroDemandeEtab: 'AAAAAAA',
      nomEtab: 'AAAAAAA',
      typeEtab: TypeEtab.LyceeTechnique,
      statut: StatutEtab.Prive,
      emailEtab: 'AAAAAAA',
      region: NomReg.Dakar,
      departement: NomDep.Dakar,
      commune: 'AAAAAAA',
      typeInsp: TypeInspection.IA,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DemandeMatriculeEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DemandeMatriculeEtab()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeMatriculeEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroDemandeEtab: 'BBBBBB',
          nomEtab: 'BBBBBB',
          typeEtab: 'BBBBBB',
          statut: 'BBBBBB',
          emailEtab: 'BBBBBB',
          region: 'BBBBBB',
          departement: 'BBBBBB',
          commune: 'BBBBBB',
          typeInsp: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeMatriculeEtab', () => {
      const patchObject = Object.assign(
        {
          numeroDemandeEtab: 'BBBBBB',
          nomEtab: 'BBBBBB',
          statut: 'BBBBBB',
          departement: 'BBBBBB',
          commune: 'BBBBBB',
          typeInsp: 'BBBBBB',
        },
        new DemandeMatriculeEtab()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeMatriculeEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroDemandeEtab: 'BBBBBB',
          nomEtab: 'BBBBBB',
          typeEtab: 'BBBBBB',
          statut: 'BBBBBB',
          emailEtab: 'BBBBBB',
          region: 'BBBBBB',
          departement: 'BBBBBB',
          commune: 'BBBBBB',
          typeInsp: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DemandeMatriculeEtab', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDemandeMatriculeEtabToCollectionIfMissing', () => {
      it('should add a DemandeMatriculeEtab to an empty array', () => {
        const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 123 };
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing([], demandeMatriculeEtab);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeMatriculeEtab);
      });

      it('should not add a DemandeMatriculeEtab to an array that contains it', () => {
        const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 123 };
        const demandeMatriculeEtabCollection: IDemandeMatriculeEtab[] = [
          {
            ...demandeMatriculeEtab,
          },
          { id: 456 },
        ];
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing(demandeMatriculeEtabCollection, demandeMatriculeEtab);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeMatriculeEtab to an array that doesn't contain it", () => {
        const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 123 };
        const demandeMatriculeEtabCollection: IDemandeMatriculeEtab[] = [{ id: 456 }];
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing(demandeMatriculeEtabCollection, demandeMatriculeEtab);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeMatriculeEtab);
      });

      it('should add only unique DemandeMatriculeEtab to an array', () => {
        const demandeMatriculeEtabArray: IDemandeMatriculeEtab[] = [{ id: 123 }, { id: 456 }, { id: 83314 }];
        const demandeMatriculeEtabCollection: IDemandeMatriculeEtab[] = [{ id: 123 }];
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing(demandeMatriculeEtabCollection, ...demandeMatriculeEtabArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 123 };
        const demandeMatriculeEtab2: IDemandeMatriculeEtab = { id: 456 };
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing([], demandeMatriculeEtab, demandeMatriculeEtab2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeMatriculeEtab);
        expect(expectedResult).toContain(demandeMatriculeEtab2);
      });

      it('should accept null and undefined values', () => {
        const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 123 };
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing([], null, demandeMatriculeEtab, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeMatriculeEtab);
      });

      it('should return initial array if no DemandeMatriculeEtab is added', () => {
        const demandeMatriculeEtabCollection: IDemandeMatriculeEtab[] = [{ id: 123 }];
        expectedResult = service.addDemandeMatriculeEtabToCollectionIfMissing(demandeMatriculeEtabCollection, undefined, null);
        expect(expectedResult).toEqual(demandeMatriculeEtabCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
