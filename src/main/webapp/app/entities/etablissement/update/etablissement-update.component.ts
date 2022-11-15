import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtablissement, Etablissement } from '../etablissement.model';
import { EtablissementService } from '../service/etablissement.service';
import { IDemandeMatriculeEtab } from 'app/entities/demande-matricule-etab/demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from 'app/entities/demande-matricule-etab/service/demande-matricule-etab.service';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

@Component({
  selector: 'jhi-etablissement-update',
  templateUrl: './etablissement-update.component.html',
})
export class EtablissementUpdateComponent implements OnInit {
  isSaving = false;
  typeEtabValues = Object.keys(TypeEtab);
  statutEtabValues = Object.keys(StatutEtab);
  nomRegValues = Object.keys(NomReg);
  nomDepValues = Object.keys(NomDep);
  typeInspectionValues = Object.keys(TypeInspection);

  demandeMatriculeEtabsCollection: IDemandeMatriculeEtab[] = [];

  editForm = this.fb.group({
    id: [],
    matriculeEtab: [null, []],
    nomEtab: [null, [Validators.required]],
    typeEtab: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    emailEtab: [null, []],
    region: [null, [Validators.required]],
    departement: [null, [Validators.required]],
    commune: [null, [Validators.required]],
    typeInsp: [null, [Validators.required]],
    demandeMatriculeEtab: [],
  });

  constructor(
    protected etablissementService: EtablissementService,
    protected demandeMatriculeEtabService: DemandeMatriculeEtabService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablissement }) => {
      this.updateForm(etablissement);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etablissement = this.createFromForm();
    if (etablissement.id !== undefined) {
      this.subscribeToSaveResponse(this.etablissementService.update(etablissement));
    } else {
      this.subscribeToSaveResponse(this.etablissementService.create(etablissement));
    }
  }

  trackDemandeMatriculeEtabById(index: number, item: IDemandeMatriculeEtab): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablissement>>): void {
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

  protected updateForm(etablissement: IEtablissement): void {
    this.editForm.patchValue({
      id: etablissement.id,
      matriculeEtab: etablissement.matriculeEtab,
      nomEtab: etablissement.nomEtab,
      typeEtab: etablissement.typeEtab,
      statut: etablissement.statut,
      emailEtab: etablissement.emailEtab,
      region: etablissement.region,
      departement: etablissement.departement,
      commune: etablissement.commune,
      typeInsp: etablissement.typeInsp,
      demandeMatriculeEtab: etablissement.demandeMatriculeEtab,
    });

    this.demandeMatriculeEtabsCollection = this.demandeMatriculeEtabService.addDemandeMatriculeEtabToCollectionIfMissing(
      this.demandeMatriculeEtabsCollection,
      etablissement.demandeMatriculeEtab
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeMatriculeEtabService
      .query({ filter: 'etablissement-is-null' })
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

  protected createFromForm(): IEtablissement {
    return {
      ...new Etablissement(),
      id: this.editForm.get(['id'])!.value,
      matriculeEtab: this.editForm.get(['matriculeEtab'])!.value,
      nomEtab: this.editForm.get(['nomEtab'])!.value,
      typeEtab: this.editForm.get(['typeEtab'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      emailEtab: this.editForm.get(['emailEtab'])!.value,
      region: this.editForm.get(['region'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      typeInsp: this.editForm.get(['typeInsp'])!.value,
      demandeMatriculeEtab: this.editForm.get(['demandeMatriculeEtab'])!.value,
    };
  }
}
