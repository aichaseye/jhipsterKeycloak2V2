import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeMatriculeAppService } from '../service/demande-matricule-app.service';
import { IDemandeMatriculeApp, DemandeMatriculeApp } from '../demande-matricule-app.model';

import { DemandeMatriculeAppUpdateComponent } from './demande-matricule-app-update.component';

describe('DemandeMatriculeApp Management Update Component', () => {
  let comp: DemandeMatriculeAppUpdateComponent;
  let fixture: ComponentFixture<DemandeMatriculeAppUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeMatriculeAppService: DemandeMatriculeAppService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeMatriculeAppUpdateComponent],
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
      .overrideTemplate(DemandeMatriculeAppUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeMatriculeAppUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeMatriculeAppService = TestBed.inject(DemandeMatriculeAppService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demandeMatriculeApp: IDemandeMatriculeApp = { id: 456 };

      activatedRoute.data = of({ demandeMatriculeApp });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(demandeMatriculeApp));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeApp>>();
      const demandeMatriculeApp = { id: 123 };
      jest.spyOn(demandeMatriculeAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeMatriculeApp }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeMatriculeAppService.update).toHaveBeenCalledWith(demandeMatriculeApp);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeApp>>();
      const demandeMatriculeApp = new DemandeMatriculeApp();
      jest.spyOn(demandeMatriculeAppService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeMatriculeApp }));
      saveSubject.complete();

      // THEN
      expect(demandeMatriculeAppService.create).toHaveBeenCalledWith(demandeMatriculeApp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeApp>>();
      const demandeMatriculeApp = { id: 123 };
      jest.spyOn(demandeMatriculeAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeMatriculeAppService.update).toHaveBeenCalledWith(demandeMatriculeApp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
