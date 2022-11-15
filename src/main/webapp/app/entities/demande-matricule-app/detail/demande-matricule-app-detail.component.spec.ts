import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeMatriculeAppDetailComponent } from './demande-matricule-app-detail.component';

describe('DemandeMatriculeApp Management Detail Component', () => {
  let comp: DemandeMatriculeAppDetailComponent;
  let fixture: ComponentFixture<DemandeMatriculeAppDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemandeMatriculeAppDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demandeMatriculeApp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemandeMatriculeAppDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemandeMatriculeAppDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeMatriculeApp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demandeMatriculeApp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
