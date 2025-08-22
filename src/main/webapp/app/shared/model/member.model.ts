import { ICard } from 'app/shared/model/card.model';

export interface IMember {
  id?: number;
  trelloId?: string;
  name?: string | null;
  cards?: ICard[] | null;
}

export const defaultValue: Readonly<IMember> = {};
