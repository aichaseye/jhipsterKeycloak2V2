import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Fonction } from 'app/entities/enumerations/fonction.model';
import { IPersonnelEtab, PersonnelEtab } from '../personnel-etab.model';

import { PersonnelEtabService } from './personnel-etab.service';

describe('PersonnelEtab Service', () => {
  let service: PersonnelEtabService;
  let httpMock: HttpTestingController;
  let elemDefault: IPersonnelEtab;
  let expectedResult: IPersonnelEtab | IPersonnelEtab[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonnelEtabService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      fonction: Fonction.Proviseur,
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

    it('should create a PersonnelEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PersonnelEtab()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonnelEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          fonction: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonnelEtab', () => {
      const patchObject = Object.assign(
        {
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
        },
        new PersonnelEtab()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonnelEtab', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          fonction: 'BBBBBB',
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

    it('should delete a PersonnelEtab', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPersonnelEtabToCollectionIfMissing', () => {
      it('should add a PersonnelEtab to an empty array', () => {
        const personnelEtab: IPersonnelEtab = { id: 123 };
        expectedResult = service.addPersonnelEtabToCollectionIfMissing([], personnelEtab);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personnelEtab);
      });

      it('should not add a PersonnelEtab to an array that contains it', () => {
        const personnelEtab: IPersonnelEtab = { id: 123 };
        const personnelEtabCollection: IPersonnelEtab[] = [
          {
            ...personnelEtab,
          },
          { id: 456 },
        ];
        expectedResult = service.addPersonnelEtabToCollectionIfMissing(personnelEtabCollection, personnelEtab);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonnelEtab to an array that doesn't contain it", () => {
        const personnelEtab: IPersonnelEtab = { id: 123 };
        const personnelEtabCollection: IPersonnelEtab[] = [{ id: 456 }];
        expectedResult = service.addPersonnelEtabToCollectionIfMissing(personnelEtabCollection, personnelEtab);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personnelEtab);
      });

      it('should add only unique PersonnelEtab to an array', () => {
        const personnelEtabArray: IPersonnelEtab[] = [{ id: 123 }, { id: 456 }, { id: 14733 }];
        const personnelEtabCollection: IPersonnelEtab[] = [{ id: 123 }];
        expectedResult = service.addPersonnelEtabToCollectionIfMissing(personnelEtabCollection, ...personnelEtabArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personnelEtab: IPersonnelEtab = { id: 123 };
        const personnelEtab2: IPersonnelEtab = { id: 456 };
        expectedResult = service.addPersonnelEtabToCollectionIfMissing([], personnelEtab, personnelEtab2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personnelEtab);
        expect(expectedResult).toContain(personnelEtab2);
      });

      it('should accept null and undefined values', () => {
        const personnelEtab: IPersonnelEtab = { id: 123 };
        expectedResult = service.addPersonnelEtabToCollectionIfMissing([], null, personnelEtab, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personnelEtab);
      });

      it('should return initial array if no PersonnelEtab is added', () => {
        const personnelEtabCollection: IPersonnelEtab[] = [{ id: 123 }];
        expectedResult = service.addPersonnelEtabToCollectionIfMissing(personnelEtabCollection, undefined, null);
        expect(expectedResult).toEqual(personnelEtabCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
