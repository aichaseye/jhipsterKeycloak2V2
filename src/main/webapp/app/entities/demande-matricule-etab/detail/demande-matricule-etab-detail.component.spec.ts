import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeMatriculeEtabDetailComponent } from './demande-matricule-etab-detail.component';

describe('DemandeMatriculeEtab Management Detail Component', () => {
  let comp: DemandeMatriculeEtabDetailComponent;
  let fixture: ComponentFixture<DemandeMatriculeEtabDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemandeMatriculeEtabDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demandeMatriculeEtab: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemandeMatriculeEtabDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemandeMatriculeEtabDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeMatriculeEtab on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demandeMatriculeEtab).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
