import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonnelEtabService } from '../service/personnel-etab.service';
import { IPersonnelEtab, PersonnelEtab } from '../personnel-etab.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';

import { PersonnelEtabUpdateComponent } from './personnel-etab-update.component';

describe('PersonnelEtab Management Update Component', () => {
  let comp: PersonnelEtabUpdateComponent;
  let fixture: ComponentFixture<PersonnelEtabUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personnelEtabService: PersonnelEtabService;
  let userService: UserService;
  let etablissementService: EtablissementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonnelEtabUpdateComponent],
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
      .overrideTemplate(PersonnelEtabUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonnelEtabUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personnelEtabService = TestBed.inject(PersonnelEtabService);
    userService = TestBed.inject(UserService);
    etablissementService = TestBed.inject(EtablissementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const personnelEtab: IPersonnelEtab = { id: 456 };
      const user: IUser = { id: 'a5b256bf-dc32-47e3-a9ae-1438b973484a' };
      personnelEtab.user = user;

      const userCollection: IUser[] = [{ id: '807ad48b-0d0d-4cf3-b06e-8a685081bcd6' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etablissement query and add missing value', () => {
      const personnelEtab: IPersonnelEtab = { id: 456 };
      const etablissement: IEtablissement = { id: 81560 };
      personnelEtab.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 35082 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personnelEtab: IPersonnelEtab = { id: 456 };
      const user: IUser = { id: '69655241-a87f-4ee1-a9f8-b225605d4ed6' };
      personnelEtab.user = user;
      const etablissement: IEtablissement = { id: 73973 };
      personnelEtab.etablissement = etablissement;

      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(personnelEtab));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonnelEtab>>();
      const personnelEtab = { id: 123 };
      jest.spyOn(personnelEtabService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personnelEtab }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(personnelEtabService.update).toHaveBeenCalledWith(personnelEtab);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonnelEtab>>();
      const personnelEtab = new PersonnelEtab();
      jest.spyOn(personnelEtabService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personnelEtab }));
      saveSubject.complete();

      // THEN
      expect(personnelEtabService.create).toHaveBeenCalledWith(personnelEtab);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonnelEtab>>();
      const personnelEtab = { id: 123 };
      jest.spyOn(personnelEtabService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personnelEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personnelEtabService.update).toHaveBeenCalledWith(personnelEtab);
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

    describe('trackEtablissementById', () => {
      it('Should return tracked Etablissement primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtablissementById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
