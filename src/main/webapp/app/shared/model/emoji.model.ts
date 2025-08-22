import { ITextData } from 'app/shared/model/text-data.model';

export interface IEmoji {
  id?: number;
  emojiId?: string | null;
  name?: string | null;
  textData?: ITextData | null;
}

export const defaultValue: Readonly<IEmoji> = {};
