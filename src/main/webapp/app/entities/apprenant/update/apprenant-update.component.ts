import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IApprenant, Apprenant } from '../apprenant.model';
import { ApprenantService } from '../service/apprenant.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { DemandeMatriculeAppService } from 'app/entities/demande-matricule-app/service/demande-matricule-app.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { NiveauEtudeService } from 'app/entities/niveau-etude/service/niveau-etude.service';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { SerieEtudeService } from 'app/entities/serie-etude/service/serie-etude.service';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { FiliereEtudeService } from 'app/entities/filiere-etude/service/filiere-etude.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-apprenant-update',
  templateUrl: './apprenant-update.component.html',
})
export class ApprenantUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  usersSharedCollection: IUser[] = [];
  demandeMatriculeAppsCollection: IDemandeMatriculeApp[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];
  niveauEtudesSharedCollection: INiveauEtude[] = [];
  serieEtudesSharedCollection: ISerieEtude[] = [];
  filiereEtudesSharedCollection: IFiliereEtude[] = [];

  editForm = this.fb.group({
    id: [],
    matriculeApp: [null, []],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    numIdNational: [null, [Validators.required]],
    sexe: [null, [Validators.required]],
    email: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    lieuNaissance: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    user: [],
    demandeMatriculeApp: [],
    etablissement: [],
    niveauEtude: [],
    serieEtude: [],
    filiereEtude: [],
  });

  constructor(
    protected apprenantService: ApprenantService,
    protected userService: UserService,
    protected demandeMatriculeAppService: DemandeMatriculeAppService,
    protected etablissementService: EtablissementService,
    protected niveauEtudeService: NiveauEtudeService,
    protected serieEtudeService: SerieEtudeService,
    protected filiereEtudeService: FiliereEtudeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apprenant }) => {
      this.updateForm(apprenant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apprenant = this.createFromForm();
    if (apprenant.id !== undefined) {
      this.subscribeToSaveResponse(this.apprenantService.update(apprenant));
    } else {
      this.subscribeToSaveResponse(this.apprenantService.create(apprenant));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackDemandeMatriculeAppById(index: number, item: IDemandeMatriculeApp): number {
    return item.id!;
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  trackNiveauEtudeById(index: number, item: INiveauEtude): number {
    return item.id!;
  }

  trackSerieEtudeById(index: number, item: ISerieEtude): number {
    return item.id!;
  }

  trackFiliereEtudeById(index: number, item: IFiliereEtude): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApprenant>>): void {
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

  protected updateForm(apprenant: IApprenant): void {
    this.editForm.patchValue({
      id: apprenant.id,
      matriculeApp: apprenant.matriculeApp,
      nom: apprenant.nom,
      prenom: apprenant.prenom,
      numIdNational: apprenant.numIdNational,
      sexe: apprenant.sexe,
      email: apprenant.email,
      dateNaissance: apprenant.dateNaissance,
      lieuNaissance: apprenant.lieuNaissance,
      adresse: apprenant.adresse,
      user: apprenant.user,
      demandeMatriculeApp: apprenant.demandeMatriculeApp,
      etablissement: apprenant.etablissement,
      niveauEtude: apprenant.niveauEtude,
      serieEtude: apprenant.serieEtude,
      filiereEtude: apprenant.filiereEtude,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, apprenant.user);
    this.demandeMatriculeAppsCollection = this.demandeMatriculeAppService.addDemandeMatriculeAppToCollectionIfMissing(
      this.demandeMatriculeAppsCollection,
      apprenant.demandeMatriculeApp
    );
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      apprenant.etablissement
    );
    this.niveauEtudesSharedCollection = this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(
      this.niveauEtudesSharedCollection,
      apprenant.niveauEtude
    );
    this.serieEtudesSharedCollection = this.serieEtudeService.addSerieEtudeToCollectionIfMissing(
      this.serieEtudesSharedCollection,
      apprenant.serieEtude
    );
    this.filiereEtudesSharedCollection = this.filiereEtudeService.addFiliereEtudeToCollectionIfMissing(
      this.filiereEtudesSharedCollection,
      apprenant.filiereEtude
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.demandeMatriculeAppService
      .query({ filter: 'apprenant-is-null' })
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

    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing(etablissements, this.editForm.get('etablissement')!.value)
        )
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));

    this.niveauEtudeService
      .query()
      .pipe(map((res: HttpResponse<INiveauEtude[]>) => res.body ?? []))
      .pipe(
        map((niveauEtudes: INiveauEtude[]) =>
          this.niveauEtudeService.addNiveauEtudeToCollectionIfMissing(niveauEtudes, this.editForm.get('niveauEtude')!.value)
        )
      )
      .subscribe((niveauEtudes: INiveauEtude[]) => (this.niveauEtudesSharedCollection = niveauEtudes));

    this.serieEtudeService
      .query()
      .pipe(map((res: HttpResponse<ISerieEtude[]>) => res.body ?? []))
      .pipe(
        map((serieEtudes: ISerieEtude[]) =>
          this.serieEtudeService.addSerieEtudeToCollectionIfMissing(serieEtudes, this.editForm.get('serieEtude')!.value)
        )
      )
      .subscribe((serieEtudes: ISerieEtude[]) => (this.serieEtudesSharedCollection = serieEtudes));

    this.filiereEtudeService
      .query()
      .pipe(map((res: HttpResponse<IFiliereEtude[]>) => res.body ?? []))
      .pipe(
        map((filiereEtudes: IFiliereEtude[]) =>
          this.filiereEtudeService.addFiliereEtudeToCollectionIfMissing(filiereEtudes, this.editForm.get('filiereEtude')!.value)
        )
      )
      .subscribe((filiereEtudes: IFiliereEtude[]) => (this.filiereEtudesSharedCollection = filiereEtudes));
  }

  protected createFromForm(): IApprenant {
    return {
      ...new Apprenant(),
      id: this.editForm.get(['id'])!.value,
      matriculeApp: this.editForm.get(['matriculeApp'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      numIdNational: this.editForm.get(['numIdNational'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      user: this.editForm.get(['user'])!.value,
      demandeMatriculeApp: this.editForm.get(['demandeMatriculeApp'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
      serieEtude: this.editForm.get(['serieEtude'])!.value,
      filiereEtude: this.editForm.get(['filiereEtude'])!.value,
    };
  }
}
