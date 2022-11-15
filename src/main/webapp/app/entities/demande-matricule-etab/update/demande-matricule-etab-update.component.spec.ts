import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeMatriculeEtabService } from '../service/demande-matricule-etab.service';
import { IDemandeMatriculeEtab, DemandeMatriculeEtab } from '../demande-matricule-etab.model';

import { DemandeMatriculeEtabUpdateComponent } from './demande-matricule-etab-update.component';

describe('DemandeMatriculeEtab Management Update Component', () => {
  let comp: DemandeMatriculeEtabUpdateComponent;
  let fixture: ComponentFixture<DemandeMatriculeEtabUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeMatriculeEtabService: DemandeMatriculeEtabService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeMatriculeEtabUpdateComponent],
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
      .overrideTemplate(DemandeMatriculeEtabUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeMatriculeEtabUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeMatriculeEtabService = TestBed.inject(DemandeMatriculeEtabService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demandeMatriculeEtab: IDemandeMatriculeEtab = { id: 456 };

      activatedRoute.data = of({ demandeMatriculeEtab });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(demandeMatriculeEtab));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeEtab>>();
      const demandeMatriculeEtab = { id: 123 };
      jest.spyOn(demandeMatriculeEtabService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeMatriculeEtab }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeMatriculeEtabService.update).toHaveBeenCalledWith(demandeMatriculeEtab);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeEtab>>();
      const demandeMatriculeEtab = new DemandeMatriculeEtab();
      jest.spyOn(demandeMatriculeEtabService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeMatriculeEtab }));
      saveSubject.complete();

      // THEN
      expect(demandeMatriculeEtabService.create).toHaveBeenCalledWith(demandeMatriculeEtab);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeMatriculeEtab>>();
      const demandeMatriculeEtab = { id: 123 };
      jest.spyOn(demandeMatriculeEtabService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeMatriculeEtab });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeMatriculeEtabService.update).toHaveBeenCalledWith(demandeMatriculeEtab);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
