import React from 'react';
import { Translate } from 'react-jhipster'; // eslint-disable-line

import MenuItem from 'app/shared/layout/menus/menu-item'; // eslint-disable-line

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/workspace">
        <Translate contentKey="global.menu.entities.workspace" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/board">
        <Translate contentKey="global.menu.entities.board" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/board-list">
        <Translate contentKey="global.menu.entities.boardList" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/card">
        <Translate contentKey="global.menu.entities.card" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <Translate contentKey="global.menu.entities.comment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/label">
        <Translate contentKey="global.menu.entities.label" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/attachment">
        <Translate contentKey="global.menu.entities.attachment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/trello-webhook">
        <Translate contentKey="global.menu.entities.trelloWebhook" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/check-list">
        <Translate contentKey="global.menu.entities.checkList" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/check-item">
        <Translate contentKey="global.menu.entities.checkItem" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/text-data">
        <Translate contentKey="global.menu.entities.textData" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emoji">
        <Translate contentKey="global.menu.entities.emoji" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
