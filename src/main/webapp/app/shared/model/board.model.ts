import dayjs from 'dayjs';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { BoardVisibility } from 'app/shared/model/enumerations/board-visibility.model';

export interface IBoard {
  id?: number;
  title?: string;
  description?: string | null;
  visibility?: keyof typeof BoardVisibility;
  trelloId?: string;
  createdAt?: dayjs.Dayjs;
  createdBy?: string | null;
  workspace?: IWorkspace | null;
}

export const defaultValue: Readonly<IBoard> = {};
