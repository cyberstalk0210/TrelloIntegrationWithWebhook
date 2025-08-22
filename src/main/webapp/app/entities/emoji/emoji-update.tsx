import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTextData } from 'app/entities/text-data/text-data.reducer';
import { createEntity, getEntity, reset, updateEntity } from './emoji.reducer';

export const EmojiUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const textData = useAppSelector(state => state.textData.entities);
  const emojiEntity = useAppSelector(state => state.emoji.entity);
  const loading = useAppSelector(state => state.emoji.loading);
  const updating = useAppSelector(state => state.emoji.updating);
  const updateSuccess = useAppSelector(state => state.emoji.updateSuccess);

  const handleClose = () => {
    navigate('/emoji');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTextData({}));
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
      ...emojiEntity,
      ...values,
      textData: textData.find(it => it.id.toString() === values.textData?.toString()),
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
          ...emojiEntity,
          textData: emojiEntity?.textData?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.emoji.home.createOrEditLabel" data-cy="EmojiCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.emoji.home.createOrEditLabel">Create or edit a Emoji</Translate>
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
                  id="emoji-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.emoji.emojiId')}
                id="emoji-emojiId"
                name="emojiId"
                data-cy="emojiId"
                type="text"
              />
              <ValidatedField label={translate('trilloIntegrationApp.emoji.name')} id="emoji-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                id="emoji-textData"
                name="textData"
                data-cy="textData"
                label={translate('trilloIntegrationApp.emoji.textData')}
                type="select"
              >
                <option value="" key="0" />
                {textData
                  ? textData.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emoji" replace color="info">
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

export default EmojiUpdate;
