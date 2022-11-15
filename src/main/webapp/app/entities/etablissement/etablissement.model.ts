import { IDemandeMatriculeEtab } from 'app/entities/demande-matricule-etab/demande-matricule-etab.model';
import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { IPersonnelEtab } from 'app/entities/personnel-etab/personnel-etab.model';
import { IMatiere } from 'app/entities/matiere/matiere.model';
import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

export interface IEtablissement {
  id?: number;
  matriculeEtab?: string | null;
  nomEtab?: string;
  typeEtab?: TypeEtab;
  statut?: StatutEtab;
  emailEtab?: string | null;
  region?: NomReg;
  departement?: NomDep;
  commune?: string;
  typeInsp?: TypeInspection;
  demandeMatriculeEtab?: IDemandeMatriculeEtab | null;
  apprenants?: IApprenant[] | null;
  personnelEtabs?: IPersonnelEtab[] | null;
  matieres?: IMatiere[] | null;
}

export class Etablissement implements IEtablissement {
  constructor(
    public id?: number,
    public matriculeEtab?: string | null,
    public nomEtab?: string,
    public typeEtab?: TypeEtab,
    public statut?: StatutEtab,
    public emailEtab?: string | null,
    public region?: NomReg,
    public departement?: NomDep,
    public commune?: string,
    public typeInsp?: TypeInspection,
    public demandeMatriculeEtab?: IDemandeMatriculeEtab | null,
    public apprenants?: IApprenant[] | null,
    public personnelEtabs?: IPersonnelEtab[] | null,
    public matieres?: IMatiere[] | null
  ) {}
}

export function getEtablissementIdentifier(etablissement: IEtablissement): number | undefined {
  return etablissement.id;
}
