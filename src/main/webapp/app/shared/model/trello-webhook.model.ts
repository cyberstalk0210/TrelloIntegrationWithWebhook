import dayjs from 'dayjs';

export interface ITrelloWebhook {
  id?: number;
  trelloWebhookId?: string;
  idModel?: string;
  callbackUrl?: string;
  active?: boolean | null;
  lastReceivedAt?: dayjs.Dayjs | null;
  secret?: string | null;
}

export const defaultValue: Readonly<ITrelloWebhook> = {
  active: false,
};
