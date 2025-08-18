import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './card.reducer';

export const CardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cardEntity = useAppSelector(state => state.card.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cardDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.card.detail.title">Card</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cardEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="trilloIntegrationApp.card.title">Title</Translate>
            </span>
          </dt>
          <dd>{cardEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="trilloIntegrationApp.card.description">Description</Translate>
            </span>
          </dt>
          <dd>{cardEntity.description}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="trilloIntegrationApp.card.position">Position</Translate>
            </span>
          </dt>
          <dd>{cardEntity.position}</dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="trilloIntegrationApp.card.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>{cardEntity.dueDate ? <TextFormat value={cardEntity.dueDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isArchived">
              <Translate contentKey="trilloIntegrationApp.card.isArchived">Is Archived</Translate>
            </span>
          </dt>
          <dd>{cardEntity.isArchived ? 'true' : 'false'}</dd>
          <dt>
            <span id="trelloId">
              <Translate contentKey="trilloIntegrationApp.card.trelloId">Trello Id</Translate>
            </span>
          </dt>
          <dd>{cardEntity.trelloId}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="trilloIntegrationApp.card.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{cardEntity.createdAt ? <TextFormat value={cardEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="trilloIntegrationApp.card.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{cardEntity.updatedAt ? <TextFormat value={cardEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.card.labels">Labels</Translate>
          </dt>
          <dd>
            {cardEntity.labels
              ? cardEntity.labels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {cardEntity.labels && i === cardEntity.labels.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.card.board">Board</Translate>
          </dt>
          <dd>{cardEntity.board ? cardEntity.board.id : ''}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.card.boardList">Board List</Translate>
          </dt>
          <dd>{cardEntity.boardList ? cardEntity.boardList.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/card" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/card/${cardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CardDetail;
