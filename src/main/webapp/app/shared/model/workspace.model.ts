export interface IWorkspace {
  id?: number;
  name?: string;
  trelloId?: string;
}

export const defaultValue: Readonly<IWorkspace> = {};
