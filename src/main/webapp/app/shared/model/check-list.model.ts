export interface ICheckList {
  id?: number;
  checkListId?: string | null;
  name?: string | null;
}

export const defaultValue: Readonly<ICheckList> = {};
