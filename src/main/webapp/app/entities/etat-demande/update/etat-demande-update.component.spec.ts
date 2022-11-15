import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EtatDemandeService } from '../service/etat-demande.service';
import { IEtatDemande, EtatDemande } from '../etat-demande.model';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { DemandeMatriculeAppService } from 'app/entities/demande-matricule-app/service/demande-matricule-app.service';
import { IDemandeMatriculeEtab } from 'app/entities/demande-matricule-etab/demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from 'app/entities/demande-matricule-etab/service/demande-matricule-etab.service';

import { EtatDemandeUpdateComponent } from './etat-demande-update.component';

describe('EtatDemande Management Update Component', () => {
  let comp: EtatDemandeUpdateComponent;
  let fixture: ComponentFixture<EtatDemandeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etatDemandeService: EtatDemandeService;
  let demandeMatriculeAppService: DemandeMatriculeAppService;
  let demandeMatriculeEtabService: DemandeMatriculeEtabService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EtatDemandeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EtatDemandeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtatDemandeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etatDemandeService = TestBed.inject(EtatDemandeService);
    demandeMatriculeAppService = TestBed.inject(DemandeMatriculeAppService);
    demandeMatriculeEtabService = TestBed.inject(DemandeMatriculeEtabService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call demandeMatriculeApp query and add missing value', () => {
      const etatDemande: IEtatDemande = { id: 456 };
      const demandeMatriculeApp: IDemandeMatriculeApp = { id: 54811 };
      etatDemande.demandeMatriculeApp = demandeMatriculeApp;

      const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [{ id: 215 }];
      jest.spyOn(demandeMatriculeAppService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeMatriculeAppCollection })));
      const expectedCollection: IDemandeMatriculeApp[] = [demandeMatriculeApp, ...demandeMatriculeAppCollection];
      jest.spyOn(demandeMatriculeAppService, 'addDemandeMatriculeAppToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      expect(demandeMatriculeAppService.query).toHaveBeenCalled();
      expect(demandeMatriculeAppService.addDemandeMatriculeAppToCollectionIfMissing).toHaveBeenCalledWith(
        demandeMatriculeAppCollection,
        demandeMatriculeApp
      );
      expect(comp.demandeMatriculeAppsCollection).toEqual(expectedCollection);
    });

    it('Should call demandeMatriculeEtab query and add missing value', () => {
      const etatDemande: IEtatDemande = { id: 456 };
      const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 34594 };
      etatDemande.demandeMatriculeEtab = demandeMatriculeEtab;

      const demandeMatriculeEtabCollection: IDemandeMatriculeEtab[] = [{ id: 3472 }];
      jest.spyOn(demandeMatriculeEtabService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeMatriculeEtabCollection })));
      const expectedCollection: IDemandeMatriculeEtab[] = [demandeMatriculeEtab, ...demandeMatriculeEtabCollection];
      jest.spyOn(demandeMatriculeEtabService, 'addDemandeMatriculeEtabToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      expect(demandeMatriculeEtabService.query).toHaveBeenCalled();
      expect(demandeMatriculeEtabService.addDemandeMatriculeEtabToCollectionIfMissing).toHaveBeenCalledWith(
        demandeMatriculeEtabCollection,
        demandeMatriculeEtab
      );
      expect(comp.demandeMatriculeEtabsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const etatDemande: IEtatDemande = { id: 456 };
      const demandeMatriculeApp: IDemandeMatriculeApp = { id: 26295 };
      etatDemande.demandeMatriculeApp = demandeMatriculeApp;
      const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 83740 };
      etatDemande.demandeMatriculeEtab = demandeMatriculeEtab;

      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(etatDemande));
      expect(comp.demandeMatriculeAppsCollection).toContain(demandeMatriculeApp);
      expect(comp.demandeMatriculeEtabsCollection).toContain(demandeMatriculeEtab);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = { id: 123 };
      jest.spyOn(etatDemandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etatDemande }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(etatDemandeService.update).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = new EtatDemande();
      jest.spyOn(etatDemandeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etatDemande }));
      saveSubject.complete();

      // THEN
      expect(etatDemandeService.create).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EtatDemande>>();
      const etatDemande = { id: 123 };
      jest.spyOn(etatDemandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etatDemande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etatDemandeService.update).toHaveBeenCalledWith(etatDemande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDemandeMatriculeAppById', () => {
      it('Should return tracked DemandeMatriculeApp primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeMatriculeAppById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDemandeMatriculeEtabById', () => {
      it('Should return tracked DemandeMatriculeEtab primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeMatriculeEtabById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
