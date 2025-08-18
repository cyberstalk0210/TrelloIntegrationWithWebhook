import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TrelloWebhook from './trello-webhook';
import TrelloWebhookDetail from './trello-webhook-detail';
import TrelloWebhookUpdate from './trello-webhook-update';
import TrelloWebhookDeleteDialog from './trello-webhook-delete-dialog';

const TrelloWebhookRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TrelloWebhook />} />
    <Route path="new" element={<TrelloWebhookUpdate />} />
    <Route path=":id">
      <Route index element={<TrelloWebhookDetail />} />
      <Route path="edit" element={<TrelloWebhookUpdate />} />
      <Route path="delete" element={<TrelloWebhookDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrelloWebhookRoutes;
