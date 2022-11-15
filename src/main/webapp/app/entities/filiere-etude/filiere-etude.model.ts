import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { Filiere } from 'app/entities/enumerations/filiere.model';

export interface IFiliereEtude {
  id?: number;
  filiere?: Filiere | null;
  apprenants?: IApprenant[] | null;
}

export class FiliereEtude implements IFiliereEtude {
  constructor(public id?: number, public filiere?: Filiere | null, public apprenants?: IApprenant[] | null) {}
}

export function getFiliereEtudeIdentifier(filiereEtude: IFiliereEtude): number | undefined {
  return filiereEtude.id;
}
