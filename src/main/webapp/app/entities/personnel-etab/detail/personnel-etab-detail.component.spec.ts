import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonnelEtabDetailComponent } from './personnel-etab-detail.component';

describe('PersonnelEtab Management Detail Component', () => {
  let comp: PersonnelEtabDetailComponent;
  let fixture: ComponentFixture<PersonnelEtabDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonnelEtabDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personnelEtab: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonnelEtabDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonnelEtabDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personnelEtab on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personnelEtab).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
