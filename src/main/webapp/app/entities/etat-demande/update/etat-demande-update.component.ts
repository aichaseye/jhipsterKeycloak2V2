import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtatDemande, EtatDemande } from '../etat-demande.model';
import { EtatDemandeService } from '../service/etat-demande.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { DemandeMatriculeAppService } from 'app/entities/demande-matricule-app/service/demande-matricule-app.service';
import { IDemandeMatriculeEtab } from 'app/entities/demande-matricule-etab/demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from 'app/entities/demande-matricule-etab/service/demande-matricule-etab.service';
import { Etat } from 'app/entities/enumerations/etat.model';

@Component({
  selector: 'jhi-etat-demande-update',
  templateUrl: './etat-demande-update.component.html',
})
export class EtatDemandeUpdateComponent implements OnInit {
  isSaving = false;
  etatValues = Object.keys(Etat);

  demandeMatriculeAppsCollection: IDemandeMatriculeApp[] = [];
  demandeMatriculeEtabsCollection: IDemandeMatriculeEtab[] = [];

  editForm = this.fb.group({
    id: [],
    etat: [null, [Validators.required]],
    dateDisponibilite: [],
    description: [],
    demandeMatriculeApp: [null, Validators.required],
    demandeMatriculeEtab: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected etatDemandeService: EtatDemandeService,
    protected demandeMatriculeAppService: DemandeMatriculeAppService,
    protected demandeMatriculeEtabService: DemandeMatriculeEtabService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatDemande }) => {
      this.updateForm(etatDemande);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('jhipsterbaseKeycloak2App.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatDemande = this.createFromForm();
    if (etatDemande.id !== undefined) {
      this.subscribeToSaveResponse(this.etatDemandeService.update(etatDemande));
    } else {
      this.subscribeToSaveResponse(this.etatDemandeService.create(etatDemande));
    }
  }

  trackDemandeMatriculeAppById(index: number, item: IDemandeMatriculeApp): number {
    return item.id!;
  }

  trackDemandeMatriculeEtabById(index: number, item: IDemandeMatriculeEtab): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatDemande>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(etatDemande: IEtatDemande): void {
    this.editForm.patchValue({
      id: etatDemande.id,
      etat: etatDemande.etat,
      dateDisponibilite: etatDemande.dateDisponibilite,
      description: etatDemande.description,
      demandeMatriculeApp: etatDemande.demandeMatriculeApp,
      demandeMatriculeEtab: etatDemande.demandeMatriculeEtab,
    });

    this.demandeMatriculeAppsCollection = this.demandeMatriculeAppService.addDemandeMatriculeAppToCollectionIfMissing(
      this.demandeMatriculeAppsCollection,
      etatDemande.demandeMatriculeApp
    );
    this.demandeMatriculeEtabsCollection = this.demandeMatriculeEtabService.addDemandeMatriculeEtabToCollectionIfMissing(
      this.demandeMatriculeEtabsCollection,
      etatDemande.demandeMatriculeEtab
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeMatriculeAppService
      .query({ filter: 'etatdemande-is-null' })
      .pipe(map((res: HttpResponse<IDemandeMatriculeApp[]>) => res.body ?? []))
      .pipe(
        map((demandeMatriculeApps: IDemandeMatriculeApp[]) =>
          this.demandeMatriculeAppService.addDemandeMatriculeAppToCollectionIfMissing(
            demandeMatriculeApps,
            this.editForm.get('demandeMatriculeApp')!.value
          )
        )
      )
      .subscribe((demandeMatriculeApps: IDemandeMatriculeApp[]) => (this.demandeMatriculeAppsCollection = demandeMatriculeApps));

    this.demandeMatriculeEtabService
      .query({ filter: 'etatdemande-is-null' })
      .pipe(map((res: HttpResponse<IDemandeMatriculeEtab[]>) => res.body ?? []))
      .pipe(
        map((demandeMatriculeEtabs: IDemandeMatriculeEtab[]) =>
          this.demandeMatriculeEtabService.addDemandeMatriculeEtabToCollectionIfMissing(
            demandeMatriculeEtabs,
            this.editForm.get('demandeMatriculeEtab')!.value
          )
        )
      )
      .subscribe((demandeMatriculeEtabs: IDemandeMatriculeEtab[]) => (this.demandeMatriculeEtabsCollection = demandeMatriculeEtabs));
  }

  protected createFromForm(): IEtatDemande {
    return {
      ...new EtatDemande(),
      id: this.editForm.get(['id'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      dateDisponibilite: this.editForm.get(['dateDisponibilite'])!.value,
      description: this.editForm.get(['description'])!.value,
      demandeMatriculeApp: this.editForm.get(['demandeMatriculeApp'])!.value,
      demandeMatriculeEtab: this.editForm.get(['demandeMatriculeEtab'])!.value,
    };
  }
}
