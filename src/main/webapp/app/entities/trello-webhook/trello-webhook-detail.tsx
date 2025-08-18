import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trello-webhook.reducer';

export const TrelloWebhookDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trelloWebhookEntity = useAppSelector(state => state.trelloWebhook.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trelloWebhookDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.trelloWebhook.detail.title">TrelloWebhook</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.id}</dd>
          <dt>
            <span id="trelloWebhookId">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.trelloWebhookId">Trello Webhook Id</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.trelloWebhookId}</dd>
          <dt>
            <span id="idModel">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.idModel">Id Model</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.idModel}</dd>
          <dt>
            <span id="callbackUrl">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.callbackUrl">Callback Url</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.callbackUrl}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.active">Active</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="lastReceivedAt">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.lastReceivedAt">Last Received At</Translate>
            </span>
          </dt>
          <dd>
            {trelloWebhookEntity.lastReceivedAt ? (
              <TextFormat value={trelloWebhookEntity.lastReceivedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="secret">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.secret">Secret</Translate>
            </span>
          </dt>
          <dd>{trelloWebhookEntity.secret}</dd>
        </dl>
        <Button tag={Link} to="/trello-webhook" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trello-webhook/${trelloWebhookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrelloWebhookDetail;
