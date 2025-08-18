import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attachment.reducer';

export const AttachmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">
          <Translate contentKey="trilloIntegrationApp.attachment.detail.title">Attachment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="trilloIntegrationApp.attachment.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.fileName}</dd>
          <dt>
            <span id="fileUrl">
              <Translate contentKey="trilloIntegrationApp.attachment.fileUrl">File Url</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.fileUrl}</dd>
          <dt>
            <span id="fileSize">
              <Translate contentKey="trilloIntegrationApp.attachment.fileSize">File Size</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.fileSize}</dd>
          <dt>
            <span id="uploadedAt">
              <Translate contentKey="trilloIntegrationApp.attachment.uploadedAt">Uploaded At</Translate>
            </span>
          </dt>
          <dd>
            {attachmentEntity.uploadedAt ? <TextFormat value={attachmentEntity.uploadedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="source">
              <Translate contentKey="trilloIntegrationApp.attachment.source">Source</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.source}</dd>
          <dt>
            <Translate contentKey="trilloIntegrationApp.attachment.card">Card</Translate>
          </dt>
          <dd>{attachmentEntity.card ? attachmentEntity.card.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttachmentDetail;
