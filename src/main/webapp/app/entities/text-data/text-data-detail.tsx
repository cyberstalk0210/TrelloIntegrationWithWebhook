import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './text-data.reducer';

export const TextDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const textDataEntity = useAppSelector(state => state.textData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="textDataDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.textData.detail.title">TextData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{textDataEntity.id}</dd>
          <dt>
            <span id="textDataId">
              <Translate contentKey="trilloIntegrationApp.textData.textDataId">Text Data Id</Translate>
            </span>
          </dt>
          <dd>{textDataEntity.textDataId}</dd>
        </dl>
        <Button tag={Link} to="/text-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/text-data/${textDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TextDataDetail;
