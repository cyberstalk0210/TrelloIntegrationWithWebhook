import dayjs from 'dayjs';
import { ICard } from 'app/shared/model/card.model';

export interface IAttachment {
  id?: number;
  fileName?: string;
  fileUrl?: string;
  fileSize?: number | null;
  uploadedAt?: dayjs.Dayjs | null;
  source?: string | null;
  card?: ICard | null;
}

export const defaultValue: Readonly<IAttachment> = {};
