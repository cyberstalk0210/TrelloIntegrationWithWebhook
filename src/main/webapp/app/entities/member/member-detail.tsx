import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './member.reducer';

export const MemberDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const memberEntity = useAppSelector(state => state.member.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memberDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.member.detail.title">Member</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{memberEntity.id}</dd>
          <dt>
            <span id="trelloId">
              <Translate contentKey="trilloIntegrationApp.member.trelloId">Trello Id</Translate>
            </span>
          </dt>
          <dd>{memberEntity.trelloId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="trilloIntegrationApp.member.name">Name</Translate>
            </span>
          </dt>
          <dd>{memberEntity.name}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.member.cards">Cards</Translate>
          </dt>
          <dd>
            {memberEntity.cards
              ? memberEntity.cards.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {memberEntity.cards && i === memberEntity.cards.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/member" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/member/${memberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemberDetail;
