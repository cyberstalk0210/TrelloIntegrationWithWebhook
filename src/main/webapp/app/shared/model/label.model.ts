import { ICard } from 'app/shared/model/card.model';

export interface ILabel {
  id?: number;
  name?: string;
  color?: string | null;
  trelloId?: string | null;
  cards?: ICard[] | null;
}

export const defaultValue: Readonly<ILabel> = {};
