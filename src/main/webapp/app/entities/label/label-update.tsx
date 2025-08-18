import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCards } from 'app/entities/card/card.reducer';
import { createEntity, getEntity, reset, updateEntity } from './label.reducer';

export const LabelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cards = useAppSelector(state => state.card.entities);
  const labelEntity = useAppSelector(state => state.label.entity);
  const loading = useAppSelector(state => state.label.loading);
  const updating = useAppSelector(state => state.label.updating);
  const updateSuccess = useAppSelector(state => state.label.updateSuccess);

  const handleClose = () => {
    navigate('/label');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCards({}));
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

    const entity = {
      ...labelEntity,
      ...values,
      cards: mapIdList(values.cards),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...labelEntity,
          cards: labelEntity?.cards?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.label.home.createOrEditLabel" data-cy="LabelCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.label.home.createOrEditLabel">Create or edit a Label</Translate>
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
                  id="label-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.label.name')}
                id="label-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.label.color')}
                id="label-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.label.trelloId')}
                id="label-trelloId"
                name="trelloId"
                data-cy="trelloId"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.label.cards')}
                id="label-cards"
                data-cy="cards"
                type="select"
                multiple
                name="cards"
              >
                <option value="" key="0" />
                {cards
                  ? cards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/label" replace color="info">
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

export default LabelUpdate;
