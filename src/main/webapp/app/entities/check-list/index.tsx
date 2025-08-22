import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CheckList from './check-list';
import CheckListDetail from './check-list-detail';
import CheckListUpdate from './check-list-update';
import CheckListDeleteDialog from './check-list-delete-dialog';

const CheckListRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CheckList />} />
    <Route path="new" element={<CheckListUpdate />} />
    <Route path=":id">
      <Route index element={<CheckListDetail />} />
      <Route path="edit" element={<CheckListUpdate />} />
      <Route path="delete" element={<CheckListDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CheckListRoutes;
