import { IEtablissement } from 'app/entities/etablissement/etablissement.model';

export interface IMatiere {
  id?: number;
  nomMatiere?: string;
  reference?: string;
  typeMatiere?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  matriculeMatiere?: string | null;
  etablissement?: IEtablissement | null;
}

export class Matiere implements IMatiere {
  constructor(
    public id?: number,
    public nomMatiere?: string,
    public reference?: string,
    public typeMatiere?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public matriculeMatiere?: string | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getMatiereIdentifier(matiere: IMatiere): number | undefined {
  return matiere.id;
}
