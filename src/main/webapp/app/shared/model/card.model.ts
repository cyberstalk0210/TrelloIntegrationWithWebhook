import dayjs from 'dayjs';
import { ILabel } from 'app/shared/model/label.model';
import { IBoard } from 'app/shared/model/board.model';
import { IBoardList } from 'app/shared/model/board-list.model';

export interface ICard {
  id?: number;
  title?: string;
  description?: string | null;
  position?: number | null;
  dueDate?: dayjs.Dayjs | null;
  isArchived?: boolean | null;
  trelloId?: string;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  labels?: ILabel[] | null;
  board?: IBoard | null;
  boardList?: IBoardList | null;
}

export const defaultValue: Readonly<ICard> = {
  isArchived: false,
};
