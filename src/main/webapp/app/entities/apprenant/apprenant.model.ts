import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { ISerieEtude } from 'app/entities/serie-etude/serie-etude.model';
import { IFiliereEtude } from 'app/entities/filiere-etude/filiere-etude.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IApprenant {
  id?: number;
  matriculeApp?: string | null;
  nom?: string;
  prenom?: string;
  numIdNational?: number;
  sexe?: Sexe;
  email?: string;
  dateNaissance?: dayjs.Dayjs;
  lieuNaissance?: string;
  adresse?: string;
  user?: IUser | null;
  demandeMatriculeApp?: IDemandeMatriculeApp | null;
  etablissement?: IEtablissement | null;
  niveauEtude?: INiveauEtude | null;
  serieEtude?: ISerieEtude | null;
  filiereEtude?: IFiliereEtude | null;
}

export class Apprenant implements IApprenant {
  constructor(
    public id?: number,
    public matriculeApp?: string | null,
    public nom?: string,
    public prenom?: string,
    public numIdNational?: number,
    public sexe?: Sexe,
    public email?: string,
    public dateNaissance?: dayjs.Dayjs,
    public lieuNaissance?: string,
    public adresse?: string,
    public user?: IUser | null,
    public demandeMatriculeApp?: IDemandeMatriculeApp | null,
    public etablissement?: IEtablissement | null,
    public niveauEtude?: INiveauEtude | null,
    public serieEtude?: ISerieEtude | null,
    public filiereEtude?: IFiliereEtude | null
  ) {}
}

export function getApprenantIdentifier(apprenant: IApprenant): number | undefined {
  return apprenant.id;
}
