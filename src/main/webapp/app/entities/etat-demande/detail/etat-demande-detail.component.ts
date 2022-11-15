import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatDemande } from '../etat-demande.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-etat-demande-detail',
  templateUrl: './etat-demande-detail.component.html',
})
export class EtatDemandeDetailComponent implements OnInit {
  etatDemande: IEtatDemande | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatDemande }) => {
      this.etatDemande = etatDemande;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
