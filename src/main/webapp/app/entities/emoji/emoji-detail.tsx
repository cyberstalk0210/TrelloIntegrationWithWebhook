import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './emoji.reducer';

export const EmojiDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const emojiEntity = useAppSelector(state => state.emoji.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="emojiDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.emoji.detail.title">Emoji</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{emojiEntity.id}</dd>
          <dt>
            <span id="emojiId">
              <Translate contentKey="trilloIntegrationApp.emoji.emojiId">Emoji Id</Translate>
            </span>
          </dt>
          <dd>{emojiEntity.emojiId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trilloIntegrationApp.emoji.name">Name</Translate>
            </span>
          </dt>
          <dd>{emojiEntity.name}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.emoji.textData">Text Data</Translate>
          </dt>
          <dd>{emojiEntity.textData ? emojiEntity.textData.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/emoji" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emoji/${emojiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmojiDetail;
