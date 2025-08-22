import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './check-item.reducer';

export const CheckItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const checkItemEntity = useAppSelector(state => state.checkItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="checkItemDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.checkItem.detail.title">CheckItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{checkItemEntity.id}</dd>
          <dt>
            <span id="checkItemId">
              <Translate contentKey="trilloIntegrationApp.checkItem.checkItemId">Check Item Id</Translate>
            </span>
          </dt>
          <dd>{checkItemEntity.checkItemId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trilloIntegrationApp.checkItem.name">Name</Translate>
            </span>
          </dt>
          <dd>{checkItemEntity.name}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="trilloIntegrationApp.checkItem.state">State</Translate>
            </span>
          </dt>
          <dd>{checkItemEntity.state}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.checkItem.data">Data</Translate>
          </dt>
          <dd>{checkItemEntity.data ? checkItemEntity.data.id : ''}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.checkItem.checkList">Check List</Translate>
          </dt>
          <dd>{checkItemEntity.checkList ? checkItemEntity.checkList.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/check-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/check-item/${checkItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CheckItemDetail;
