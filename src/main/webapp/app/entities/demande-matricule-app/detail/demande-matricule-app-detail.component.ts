import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeMatriculeApp } from '../demande-matricule-app.model';

@Component({
  selector: 'jhi-demande-matricule-app-detail',
  templateUrl: './demande-matricule-app-detail.component.html',
})
export class DemandeMatriculeAppDetailComponent implements OnInit {
  demandeMatriculeApp: IDemandeMatriculeApp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatriculeApp }) => {
      this.demandeMatriculeApp = demandeMatriculeApp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
