import dayjs from 'dayjs/esm';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IDemandeMatriculeApp {
  id?: number;
  numeroDemandeApp?: string | null;
  nom?: string;
  prenom?: string;
  numIdNational?: number;
  sexe?: Sexe;
  email?: string;
  dateNaissance?: dayjs.Dayjs;
  lieuNaissance?: string;
  adresse?: string;
  etabFrequente?: string;
  matriculeEtab?: string;
}

export class DemandeMatriculeApp implements IDemandeMatriculeApp {
  constructor(
    public id?: number,
    public numeroDemandeApp?: string | null,
    public nom?: string,
    public prenom?: string,
    public numIdNational?: number,
    public sexe?: Sexe,
    public email?: string,
    public dateNaissance?: dayjs.Dayjs,
    public lieuNaissance?: string,
    public adresse?: string,
    public etabFrequente?: string,
    public matriculeEtab?: string
  ) {}
}

export function getDemandeMatriculeAppIdentifier(demandeMatriculeApp: IDemandeMatriculeApp): number | undefined {
  return demandeMatriculeApp.id;
}
