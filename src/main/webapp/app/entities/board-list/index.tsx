import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BoardList from './board-list';
import BoardListDetail from './board-list-detail';
import BoardListUpdate from './board-list-update';
import BoardListDeleteDialog from './board-list-delete-dialog';

const BoardListRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BoardList />} />
    <Route path="new" element={<BoardListUpdate />} />
    <Route path=":id">
      <Route index element={<BoardListDetail />} />
      <Route path="edit" element={<BoardListUpdate />} />
      <Route path="delete" element={<BoardListDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BoardListRoutes;
