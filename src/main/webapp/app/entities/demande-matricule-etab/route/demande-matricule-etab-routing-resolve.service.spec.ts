import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDemandeMatriculeEtab, DemandeMatriculeEtab } from '../demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from '../service/demande-matricule-etab.service';

import { DemandeMatriculeEtabRoutingResolveService } from './demande-matricule-etab-routing-resolve.service';

describe('DemandeMatriculeEtab routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DemandeMatriculeEtabRoutingResolveService;
  let service: DemandeMatriculeEtabService;
  let resultDemandeMatriculeEtab: IDemandeMatriculeEtab | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DemandeMatriculeEtabRoutingResolveService);
    service = TestBed.inject(DemandeMatriculeEtabService);
    resultDemandeMatriculeEtab = undefined;
  });

  describe('resolve', () => {
    it('should return IDemandeMatriculeEtab returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeEtab = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeMatriculeEtab).toEqual({ id: 123 });
    });

    it('should return new IDemandeMatriculeEtab if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeEtab = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDemandeMatriculeEtab).toEqual(new DemandeMatriculeEtab());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandeMatriculeEtab })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeEtab = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeMatriculeEtab).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
