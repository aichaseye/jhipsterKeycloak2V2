import { IApprenant } from 'app/entities/apprenant/apprenant.model';
import { Serie } from 'app/entities/enumerations/serie.model';

export interface ISerieEtude {
  id?: number;
  serie?: Serie | null;
  apprenants?: IApprenant[] | null;
}

export class SerieEtude implements ISerieEtude {
  constructor(public id?: number, public serie?: Serie | null, public apprenants?: IApprenant[] | null) {}
}

export function getSerieEtudeIdentifier(serieEtude: ISerieEtude): number | undefined {
  return serieEtude.id;
}
