import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './board-list.reducer';

export const BoardListDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const boardListEntity = useAppSelector(state => state.boardList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="boardListDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.boardList.detail.title">BoardList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{boardListEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trilloIntegrationApp.boardList.name">Name</Translate>
            </span>
          </dt>
          <dd>{boardListEntity.name}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="trilloIntegrationApp.boardList.position">Position</Translate>
            </span>
          </dt>
          <dd>{boardListEntity.position}</dd>
          <dt>
            <span id="trelloId">
              <Translate contentKey="trilloIntegrationApp.boardList.trelloId">Trello Id</Translate>
            </span>
          </dt>
          <dd>{boardListEntity.trelloId}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.boardList.board">Board</Translate>
          </dt>
          <dd>{boardListEntity.board ? boardListEntity.board.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/board-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/board-list/${boardListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BoardListDetail;
