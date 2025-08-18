import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Workspace from './workspace';
import Board from './board';
import BoardList from './board-list';
import Card from './card';
import Comment from './comment';
import Label from './label';
import Attachment from './attachment';
import TrelloWebhook from './trello-webhook';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="workspace/*" element={<Workspace />} />
        <Route path="board/*" element={<Board />} />
        <Route path="board-list/*" element={<BoardList />} />
        <Route path="card/*" element={<Card />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="label/*" element={<Label />} />
        <Route path="attachment/*" element={<Attachment />} />
        <Route path="trello-webhook/*" element={<TrelloWebhook />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
