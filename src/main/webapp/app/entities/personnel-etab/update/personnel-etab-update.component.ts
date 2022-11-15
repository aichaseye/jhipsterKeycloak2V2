import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPersonnelEtab, PersonnelEtab } from '../personnel-etab.model';
import { PersonnelEtabService } from '../service/personnel-etab.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { Fonction } from 'app/entities/enumerations/fonction.model';

@Component({
  selector: 'jhi-personnel-etab-update',
  templateUrl: './personnel-etab-update.component.html',
})
export class PersonnelEtabUpdateComponent implements OnInit {
  isSaving = false;
  fonctionValues = Object.keys(Fonction);

  usersSharedCollection: IUser[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    fonction: [null, [Validators.required]],
    user: [],
    etablissement: [],
  });

  constructor(
    protected personnelEtabService: PersonnelEtabService,
    protected userService: UserService,
    protected etablissementService: EtablissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnelEtab }) => {
      this.updateForm(personnelEtab);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personnelEtab = this.createFromForm();
    if (personnelEtab.id !== undefined) {
      this.subscribeToSaveResponse(this.personnelEtabService.update(personnelEtab));
    } else {
      this.subscribeToSaveResponse(this.personnelEtabService.create(personnelEtab));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonnelEtab>>): void {
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

  protected updateForm(personnelEtab: IPersonnelEtab): void {
    this.editForm.patchValue({
      id: personnelEtab.id,
      nom: personnelEtab.nom,
      prenom: personnelEtab.prenom,
      fonction: personnelEtab.fonction,
      user: personnelEtab.user,
      etablissement: personnelEtab.etablissement,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, personnelEtab.user);
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      personnelEtab.etablissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

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

  protected createFromForm(): IPersonnelEtab {
    return {
      ...new PersonnelEtab(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      fonction: this.editForm.get(['fonction'])!.value,
      user: this.editForm.get(['user'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
    };
  }
}
