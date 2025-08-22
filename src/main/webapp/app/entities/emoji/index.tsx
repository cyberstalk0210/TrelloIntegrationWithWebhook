import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Emoji from './emoji';
import EmojiDetail from './emoji-detail';
import EmojiUpdate from './emoji-update';
import EmojiDeleteDialog from './emoji-delete-dialog';

const EmojiRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Emoji />} />
    <Route path="new" element={<EmojiUpdate />} />
    <Route path=":id">
      <Route index element={<EmojiDetail />} />
      <Route path="edit" element={<EmojiUpdate />} />
      <Route path="delete" element={<EmojiDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmojiRoutes;
