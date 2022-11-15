import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDemandeMatriculeApp, DemandeMatriculeApp } from '../demande-matricule-app.model';
import { DemandeMatriculeAppService } from '../service/demande-matricule-app.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-demande-matricule-app-update',
  templateUrl: './demande-matricule-app-update.component.html',
})
export class DemandeMatriculeAppUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  editForm = this.fb.group({
    id: [],
    numeroDemandeApp: [null, []],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    numIdNational: [null, [Validators.required]],
    sexe: [null, [Validators.required]],
    email: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    lieuNaissance: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    etabFrequente: [null, [Validators.required]],
    matriculeEtab: [null, [Validators.required]],
  });

  constructor(
    protected demandeMatriculeAppService: DemandeMatriculeAppService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatriculeApp }) => {
      this.updateForm(demandeMatriculeApp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeMatriculeApp = this.createFromForm();
    if (demandeMatriculeApp.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeMatriculeAppService.update(demandeMatriculeApp));
    } else {
      this.subscribeToSaveResponse(this.demandeMatriculeAppService.create(demandeMatriculeApp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeMatriculeApp>>): void {
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

  protected updateForm(demandeMatriculeApp: IDemandeMatriculeApp): void {
    this.editForm.patchValue({
      id: demandeMatriculeApp.id,
      numeroDemandeApp: demandeMatriculeApp.numeroDemandeApp,
      nom: demandeMatriculeApp.nom,
      prenom: demandeMatriculeApp.prenom,
      numIdNational: demandeMatriculeApp.numIdNational,
      sexe: demandeMatriculeApp.sexe,
      email: demandeMatriculeApp.email,
      dateNaissance: demandeMatriculeApp.dateNaissance,
      lieuNaissance: demandeMatriculeApp.lieuNaissance,
      adresse: demandeMatriculeApp.adresse,
      etabFrequente: demandeMatriculeApp.etabFrequente,
      matriculeEtab: demandeMatriculeApp.matriculeEtab,
    });
  }

  protected createFromForm(): IDemandeMatriculeApp {
    return {
      ...new DemandeMatriculeApp(),
      id: this.editForm.get(['id'])!.value,
      numeroDemandeApp: this.editForm.get(['numeroDemandeApp'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      numIdNational: this.editForm.get(['numIdNational'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      etabFrequente: this.editForm.get(['etabFrequente'])!.value,
      matriculeEtab: this.editForm.get(['matriculeEtab'])!.value,
    };
  }
}
