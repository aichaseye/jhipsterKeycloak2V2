import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDemandeMatriculeEtab, DemandeMatriculeEtab } from '../demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from '../service/demande-matricule-etab.service';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

@Component({
  selector: 'jhi-demande-matricule-etab-update',
  templateUrl: './demande-matricule-etab-update.component.html',
})
export class DemandeMatriculeEtabUpdateComponent implements OnInit {
  isSaving = false;
  typeEtabValues = Object.keys(TypeEtab);
  statutEtabValues = Object.keys(StatutEtab);
  nomRegValues = Object.keys(NomReg);
  nomDepValues = Object.keys(NomDep);
  typeInspectionValues = Object.keys(TypeInspection);

  editForm = this.fb.group({
    id: [],
    numeroDemandeEtab: [null, []],
    nomEtab: [null, [Validators.required]],
    typeEtab: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    emailEtab: [null, [Validators.required]],
    region: [null, [Validators.required]],
    departement: [null, [Validators.required]],
    commune: [null, [Validators.required]],
    typeInsp: [null, [Validators.required]],
  });

  constructor(
    protected demandeMatriculeEtabService: DemandeMatriculeEtabService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeMatriculeEtab }) => {
      this.updateForm(demandeMatriculeEtab);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeMatriculeEtab = this.createFromForm();
    if (demandeMatriculeEtab.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeMatriculeEtabService.update(demandeMatriculeEtab));
    } else {
      this.subscribeToSaveResponse(this.demandeMatriculeEtabService.create(demandeMatriculeEtab));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeMatriculeEtab>>): void {
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

  protected updateForm(demandeMatriculeEtab: IDemandeMatriculeEtab): void {
    this.editForm.patchValue({
      id: demandeMatriculeEtab.id,
      numeroDemandeEtab: demandeMatriculeEtab.numeroDemandeEtab,
      nomEtab: demandeMatriculeEtab.nomEtab,
      typeEtab: demandeMatriculeEtab.typeEtab,
      statut: demandeMatriculeEtab.statut,
      emailEtab: demandeMatriculeEtab.emailEtab,
      region: demandeMatriculeEtab.region,
      departement: demandeMatriculeEtab.departement,
      commune: demandeMatriculeEtab.commune,
      typeInsp: demandeMatriculeEtab.typeInsp,
    });
  }

  protected createFromForm(): IDemandeMatriculeEtab {
    return {
      ...new DemandeMatriculeEtab(),
      id: this.editForm.get(['id'])!.value,
      numeroDemandeEtab: this.editForm.get(['numeroDemandeEtab'])!.value,
      nomEtab: this.editForm.get(['nomEtab'])!.value,
      typeEtab: this.editForm.get(['typeEtab'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      emailEtab: this.editForm.get(['emailEtab'])!.value,
      region: this.editForm.get(['region'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      typeInsp: this.editForm.get(['typeInsp'])!.value,
    };
  }
}
