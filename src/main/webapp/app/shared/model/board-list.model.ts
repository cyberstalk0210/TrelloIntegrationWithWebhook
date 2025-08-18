import { IBoard } from 'app/shared/model/board.model';

export interface IBoardList {
  id?: number;
  name?: string;
  position?: number | null;
  trelloId?: string;
  board?: IBoard | null;
}

export const defaultValue: Readonly<IBoardList> = {};
