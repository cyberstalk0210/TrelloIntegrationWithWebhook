import dayjs from 'dayjs';
import { ICard } from 'app/shared/model/card.model';

export interface IComment {
  id?: number;
  author?: string | null;
  text?: string;
  createdAt?: dayjs.Dayjs;
  card?: ICard | null;
}

export const defaultValue: Readonly<IComment> = {};
