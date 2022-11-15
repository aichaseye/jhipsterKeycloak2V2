import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMatiere, Matiere } from '../matiere.model';
import { MatiereService } from '../service/matiere.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html',
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;

  etablissementsSharedCollection: IEtablissement[] = [];

  editForm = this.fb.group({
    id: [],
    nomMatiere: [null, [Validators.required]],
    reference: [null, [Validators.required]],
    typeMatiere: [],
    image: [],
    imageContentType: [],
    matriculeMatiere: [null, []],
    etablissement: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected matiereService: MatiereService,
    protected etablissementService: EtablissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.updateForm(matiere);

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
    const matiere = this.createFromForm();
    if (matiere.id !== undefined) {
      this.subscribeToSaveResponse(this.matiereService.update(matiere));
    } else {
      this.subscribeToSaveResponse(this.matiereService.create(matiere));
    }
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
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

  protected updateForm(matiere: IMatiere): void {
    this.editForm.patchValue({
      id: matiere.id,
      nomMatiere: matiere.nomMatiere,
      reference: matiere.reference,
      typeMatiere: matiere.typeMatiere,
      image: matiere.image,
      imageContentType: matiere.imageContentType,
      matriculeMatiere: matiere.matriculeMatiere,
      etablissement: matiere.etablissement,
    });

    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      matiere.etablissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing(etablissements, this.editForm.get('etablissement')!.value)
        )
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));
  }

  protected createFromForm(): IMatiere {
    return {
      ...new Matiere(),
      id: this.editForm.get(['id'])!.value,
      nomMatiere: this.editForm.get(['nomMatiere'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      typeMatiere: this.editForm.get(['typeMatiere'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      matriculeMatiere: this.editForm.get(['matriculeMatiere'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
    };
  }
}
