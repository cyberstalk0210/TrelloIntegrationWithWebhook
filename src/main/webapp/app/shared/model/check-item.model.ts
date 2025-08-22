import { ITextData } from 'app/shared/model/text-data.model';
import { ICheckList } from 'app/shared/model/check-list.model';
import { CheckItemState } from 'app/shared/model/enumerations/check-item-state.model';

export interface ICheckItem {
  id?: number;
  checkItemId?: string | null;
  name?: string | null;
  state?: keyof typeof CheckItemState | null;
  data?: ITextData | null;
  checkList?: ICheckList | null;
}

export const defaultValue: Readonly<ICheckItem> = {};
