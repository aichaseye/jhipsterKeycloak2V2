import { TypeEtab } from 'app/entities/enumerations/type-etab.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { NomReg } from 'app/entities/enumerations/nom-reg.model';
import { NomDep } from 'app/entities/enumerations/nom-dep.model';
import { TypeInspection } from 'app/entities/enumerations/type-inspection.model';

export interface IDemandeMatriculeEtab {
  id?: number;
  numeroDemandeEtab?: string | null;
  nomEtab?: string;
  typeEtab?: TypeEtab;
  statut?: StatutEtab;
  emailEtab?: string;
  region?: NomReg;
  departement?: NomDep;
  commune?: string;
  typeInsp?: TypeInspection;
}

export class DemandeMatriculeEtab implements IDemandeMatriculeEtab {
  constructor(
    public id?: number,
    public numeroDemandeEtab?: string | null,
    public nomEtab?: string,
    public typeEtab?: TypeEtab,
    public statut?: StatutEtab,
    public emailEtab?: string,
    public region?: NomReg,
    public departement?: NomDep,
    public commune?: string,
    public typeInsp?: TypeInspection
  ) {}
}

export function getDemandeMatriculeEtabIdentifier(demandeMatriculeEtab: IDemandeMatriculeEtab): number | undefined {
  return demandeMatriculeEtab.id;
}
