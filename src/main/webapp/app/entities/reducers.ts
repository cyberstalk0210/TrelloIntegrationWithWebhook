import workspace from 'app/entities/workspace/workspace.reducer';
import board from 'app/entities/board/board.reducer';
import boardList from 'app/entities/board-list/board-list.reducer';
import card from 'app/entities/card/card.reducer';
import comment from 'app/entities/comment/comment.reducer';
import label from 'app/entities/label/label.reducer';
import attachment from 'app/entities/attachment/attachment.reducer';
import trelloWebhook from 'app/entities/trello-webhook/trello-webhook.reducer';
import checkList from 'app/entities/check-list/check-list.reducer';
import checkItem from 'app/entities/check-item/check-item.reducer';
import textData from 'app/entities/text-data/text-data.reducer';
import emoji from 'app/entities/emoji/emoji.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  workspace,
  board,
  boardList,
  card,
  comment,
  label,
  attachment,
  trelloWebhook,
  checkList,
  checkItem,
  textData,
  emoji,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
