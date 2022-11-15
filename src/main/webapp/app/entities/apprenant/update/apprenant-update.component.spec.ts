import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApprenantService } from '../service/apprenant.service';
import { IApprenant, Apprenant } from '../apprenant.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { DemandeMatriculeAppService } from 'app/entities/demande-matricule-app/service/demande-matricule-app.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';

import { ApprenantUpdateComponent } from './apprenant-update.component';

describe('Apprenant Management Update Component', () => {
  let comp: ApprenantUpdateComponent;
  let fixture: ComponentFixture<ApprenantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apprenantService: ApprenantService;
  let userService: UserService;
  let demandeMatriculeAppService: DemandeMatriculeAppService;
  let etablissementService: EtablissementService;
  let niveauEtudeService: NiveauEtudeService;
  let serieEtudeService: SerieEtudeService;
  let filiereEtudeService: FiliereEtudeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApprenantUpdateComponent],
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
      .overrideTemplate(ApprenantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApprenantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apprenantService = TestBed.inject(ApprenantService);
    userService = TestBed.inject(UserService);
    demandeMatriculeAppService = TestBed.inject(DemandeMatriculeAppService);
    etablissementService = TestBed.inject(EtablissementService);
    niveauEtudeService = TestBed.inject(NiveauEtudeService);
    serieEtudeService = TestBed.inject(SerieEtudeService);
    filiereEtudeService = TestBed.inject(FiliereEtudeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const user: IUser = { id: 'e725e780-b108-4b8e-9413-79e42bdd60d4' };
      apprenant.user = user;

      const userCollection: IUser[] = [{ id: 'd161b3e6-c85f-4d62-8b7d-1dc6e50d8891' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call demandeMatriculeApp query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const demandeMatriculeApp: IDemandeMatriculeApp = { id: 9158 };
      apprenant.demandeMatriculeApp = demandeMatriculeApp;

      const demandeMatriculeAppCollection: IDemandeMatriculeApp[] = [{ id: 3002 }];
      jest.spyOn(demandeMatriculeAppService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeMatriculeAppCollection })));
      const expectedCollection: IDemandeMatriculeApp[] = [demandeMatriculeApp, ...demandeMatriculeAppCollection];
      jest.spyOn(demandeMatriculeAppService, 'addDemandeMatriculeAppToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(demandeMatriculeAppService.query).toHaveBeenCalled();
      expect(demandeMatriculeAppService.addDemandeMatriculeAppToCollectionIfMissing).toHaveBeenCalledWith(
        demandeMatriculeAppCollection,
        demandeMatriculeApp
      );
      expect(comp.demandeMatriculeAppsCollection).toEqual(expectedCollection);
    });

    it('Should call Etablissement query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const etablissement: IEtablissement = { id: 92984 };
      apprenant.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 4021 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NiveauEtude query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const niveauEtude: INiveauEtude = { id: 94298 };
      apprenant.niveauEtude = niveauEtude;

      const niveauEtudeCollection: INiveauEtude[] = [{ id: 57875 }];
      jest.spyOn(niveauEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: niveauEtudeCollection })));
      const additionalNiveauEtudes = [niveauEtude];
      const expectedCollection: INiveauEtude[] = [...additionalNiveauEtudes, ...niveauEtudeCollection];
      jest.spyOn(niveauEtudeService, 'addNiveauEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(niveauEtudeService.query).toHaveBeenCalled();
      expect(niveauEtudeService.addNiveauEtudeToCollectionIfMissing).toHaveBeenCalledWith(niveauEtudeCollection, ...additionalNiveauEtudes);
      expect(comp.niveauEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SerieEtude query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const serieEtude: ISerieEtude = { id: 63049 };
      apprenant.serieEtude = serieEtude;

      const serieEtudeCollection: ISerieEtude[] = [{ id: 77294 }];
      jest.spyOn(serieEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: serieEtudeCollection })));
      const additionalSerieEtudes = [serieEtude];
      const expectedCollection: ISerieEtude[] = [...additionalSerieEtudes, ...serieEtudeCollection];
      jest.spyOn(serieEtudeService, 'addSerieEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(serieEtudeService.query).toHaveBeenCalled();
      expect(serieEtudeService.addSerieEtudeToCollectionIfMissing).toHaveBeenCalledWith(serieEtudeCollection, ...additionalSerieEtudes);
      expect(comp.serieEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FiliereEtude query and add missing value', () => {
      const apprenant: IApprenant = { id: 456 };
      const filiereEtude: IFiliereEtude = { id: 22554 };
      apprenant.filiereEtude = filiereEtude;

      const filiereEtudeCollection: IFiliereEtude[] = [{ id: 19386 }];
      jest.spyOn(filiereEtudeService, 'query').mockReturnValue(of(new HttpResponse({ body: filiereEtudeCollection })));
      const additionalFiliereEtudes = [filiereEtude];
      const expectedCollection: IFiliereEtude[] = [...additionalFiliereEtudes, ...filiereEtudeCollection];
      jest.spyOn(filiereEtudeService, 'addFiliereEtudeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(filiereEtudeService.query).toHaveBeenCalled();
      expect(filiereEtudeService.addFiliereEtudeToCollectionIfMissing).toHaveBeenCalledWith(
        filiereEtudeCollection,
        ...additionalFiliereEtudes
      );
      expect(comp.filiereEtudesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const apprenant: IApprenant = { id: 456 };
      const user: IUser = { id: '437e269e-d243-43e1-9703-e8939cd2999c' };
      apprenant.user = user;
      const demandeMatriculeApp: IDemandeMatriculeApp = { id: 30769 };
      apprenant.demandeMatriculeApp = demandeMatriculeApp;
      const etablissement: IEtablissement = { id: 36316 };
      apprenant.etablissement = etablissement;
      const niveauEtude: INiveauEtude = { id: 72805 };
      apprenant.niveauEtude = niveauEtude;
      const serieEtude: ISerieEtude = { id: 91402 };
      apprenant.serieEtude = serieEtude;
      const filiereEtude: IFiliereEtude = { id: 47984 };
      apprenant.filiereEtude = filiereEtude;

      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(apprenant));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.demandeMatriculeAppsCollection).toContain(demandeMatriculeApp);
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
      expect(comp.niveauEtudesSharedCollection).toContain(niveauEtude);
      expect(comp.serieEtudesSharedCollection).toContain(serieEtude);
      expect(comp.filiereEtudesSharedCollection).toContain(filiereEtude);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = { id: 123 };
      jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apprenant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = new Apprenant();
      jest.spyOn(apprenantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apprenant }));
      saveSubject.complete();

      // THEN
      expect(apprenantService.create).toHaveBeenCalledWith(apprenant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apprenant>>();
      const apprenant = { id: 123 };
      jest.spyOn(apprenantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apprenant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apprenantService.update).toHaveBeenCalledWith(apprenant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDemandeMatriculeAppById', () => {
      it('Should return tracked DemandeMatriculeApp primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeMatriculeAppById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEtablissementById', () => {
      it('Should return tracked Etablissement primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtablissementById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNiveauEtudeById', () => {
      it('Should return tracked NiveauEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNiveauEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSerieEtudeById', () => {
      it('Should return tracked SerieEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSerieEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFiliereEtudeById', () => {
      it('Should return tracked FiliereEtude primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFiliereEtudeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
