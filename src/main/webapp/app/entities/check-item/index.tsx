import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CheckItem from './check-item';
import CheckItemDetail from './check-item-detail';
import CheckItemUpdate from './check-item-update';
import CheckItemDeleteDialog from './check-item-delete-dialog';

const CheckItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CheckItem />} />
    <Route path="new" element={<CheckItemUpdate />} />
    <Route path=":id">
      <Route index element={<CheckItemDetail />} />
      <Route path="edit" element={<CheckItemUpdate />} />
      <Route path="delete" element={<CheckItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CheckItemRoutes;
