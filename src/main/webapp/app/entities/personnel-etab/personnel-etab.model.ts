import { IUser } from 'app/entities/user/user.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { Fonction } from 'app/entities/enumerations/fonction.model';

export interface IPersonnelEtab {
  id?: number;
  nom?: string;
  prenom?: string;
  fonction?: Fonction;
  user?: IUser | null;
  etablissement?: IEtablissement | null;
}

export class PersonnelEtab implements IPersonnelEtab {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public fonction?: Fonction,
    public user?: IUser | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getPersonnelEtabIdentifier(personnelEtab: IPersonnelEtab): number | undefined {
  return personnelEtab.id;
}
