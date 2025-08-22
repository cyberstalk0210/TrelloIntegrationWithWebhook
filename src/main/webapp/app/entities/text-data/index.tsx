import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TextData from './text-data';
import TextDataDetail from './text-data-detail';
import TextDataUpdate from './text-data-update';
import TextDataDeleteDialog from './text-data-delete-dialog';

const TextDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TextData />} />
    <Route path="new" element={<TextDataUpdate />} />
    <Route path=":id">
      <Route index element={<TextDataDetail />} />
      <Route path="edit" element={<TextDataUpdate />} />
      <Route path="delete" element={<TextDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TextDataRoutes;
