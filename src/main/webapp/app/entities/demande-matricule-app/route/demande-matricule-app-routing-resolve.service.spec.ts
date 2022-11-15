import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDemandeMatriculeApp, DemandeMatriculeApp } from '../demande-matricule-app.model';
import { DemandeMatriculeAppService } from '../service/demande-matricule-app.service';

import { DemandeMatriculeAppRoutingResolveService } from './demande-matricule-app-routing-resolve.service';

describe('DemandeMatriculeApp routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DemandeMatriculeAppRoutingResolveService;
  let service: DemandeMatriculeAppService;
  let resultDemandeMatriculeApp: IDemandeMatriculeApp | undefined;

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
    routingResolveService = TestBed.inject(DemandeMatriculeAppRoutingResolveService);
    service = TestBed.inject(DemandeMatriculeAppService);
    resultDemandeMatriculeApp = undefined;
  });

  describe('resolve', () => {
    it('should return IDemandeMatriculeApp returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeApp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeMatriculeApp).toEqual({ id: 123 });
    });

    it('should return new IDemandeMatriculeApp if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeApp = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDemandeMatriculeApp).toEqual(new DemandeMatriculeApp());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DemandeMatriculeApp })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDemandeMatriculeApp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDemandeMatriculeApp).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
