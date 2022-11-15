import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeMatriculeEtab } from '../demande-matricule-etab.model';

@Component({
  selector: 'jhi-demande-matricule-etab-detail',
  templateUrl: './demande-matricule-etab-detail.component.html',
})
export class DemandeMatriculeEtabDetailComponent implements OnInit {
  demandeMatriculeEtab: IDemandeMatriculeEtab | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatriculeEtab }) => {
      this.demandeMatriculeEtab = demandeMatriculeEtab;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
