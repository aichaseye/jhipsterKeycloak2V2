import dayjs from 'dayjs/esm';
import { IDemandeMatriculeApp } from 'app/entities/demande-matricule-app/demande-matricule-app.model';
import { IDemandeMatriculeEtab } from 'app/entities/demande-matricule-etab/demande-matricule-etab.model';
import { Etat } from 'app/entities/enumerations/etat.model';

export interface IEtatDemande {
  id?: number;
  etat?: Etat;
  dateDisponibilite?: dayjs.Dayjs | null;
  description?: string | null;
  demandeMatriculeApp?: IDemandeMatriculeApp;
  demandeMatriculeEtab?: IDemandeMatriculeEtab | null;
}

export class EtatDemande implements IEtatDemande {
  constructor(
    public id?: number,
    public etat?: Etat,
    public dateDisponibilite?: dayjs.Dayjs | null,
    public description?: string | null,
    public demandeMatriculeApp?: IDemandeMatriculeApp,
    public demandeMatriculeEtab?: IDemandeMatriculeEtab | null
  ) {}
}

export function getEtatDemandeIdentifier(etatDemande: IEtatDemande): number | undefined {
  return etatDemande.id;
}
