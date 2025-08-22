import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './check-list.reducer';

export const CheckListDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const checkListEntity = useAppSelector(state => state.checkList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="checkListDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.checkList.detail.title">CheckList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{checkListEntity.id}</dd>
          <dt>
            <span id="checkListId">
              <Translate contentKey="trilloIntegrationApp.checkList.checkListId">Check List Id</Translate>
            </span>
          </dt>
          <dd>{checkListEntity.checkListId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trilloIntegrationApp.checkList.name">Name</Translate>
            </span>
          </dt>
          <dd>{checkListEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/check-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/check-list/${checkListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CheckListDetail;
