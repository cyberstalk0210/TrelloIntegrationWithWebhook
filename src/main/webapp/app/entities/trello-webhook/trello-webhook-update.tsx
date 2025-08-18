import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './trello-webhook.reducer';

export const TrelloWebhookUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const trelloWebhookEntity = useAppSelector(state => state.trelloWebhook.entity);
  const loading = useAppSelector(state => state.trelloWebhook.loading);
  const updating = useAppSelector(state => state.trelloWebhook.updating);
  const updateSuccess = useAppSelector(state => state.trelloWebhook.updateSuccess);

  const handleClose = () => {
    navigate('/trello-webhook');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.lastReceivedAt = convertDateTimeToServer(values.lastReceivedAt);

    const entity = {
      ...trelloWebhookEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          lastReceivedAt: displayDefaultDateTime(),
        }
      : {
          ...trelloWebhookEntity,
          lastReceivedAt: convertDateTimeFromServer(trelloWebhookEntity.lastReceivedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.trelloWebhook.home.createOrEditLabel" data-cy="TrelloWebhookCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.trelloWebhook.home.createOrEditLabel">Create or edit a TrelloWebhook</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="trello-webhook-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.trelloWebhookId')}
                id="trello-webhook-trelloWebhookId"
                name="trelloWebhookId"
                data-cy="trelloWebhookId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.idModel')}
                id="trello-webhook-idModel"
                name="idModel"
                data-cy="idModel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.callbackUrl')}
                id="trello-webhook-callbackUrl"
                name="callbackUrl"
                data-cy="callbackUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.active')}
                id="trello-webhook-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.lastReceivedAt')}
                id="trello-webhook-lastReceivedAt"
                name="lastReceivedAt"
                data-cy="lastReceivedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.trelloWebhook.secret')}
                id="trello-webhook-secret"
                name="secret"
                data-cy="secret"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trello-webhook" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TrelloWebhookUpdate;
