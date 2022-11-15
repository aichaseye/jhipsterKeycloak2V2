import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonnelEtab } from '../personnel-etab.model';

@Component({
  selector: 'jhi-personnel-etab-detail',
  templateUrl: './personnel-etab-detail.component.html',
})
export class PersonnelEtabDetailComponent implements OnInit {
  personnelEtab: IPersonnelEtab | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnelEtab }) => {
      this.personnelEtab = personnelEtab;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
