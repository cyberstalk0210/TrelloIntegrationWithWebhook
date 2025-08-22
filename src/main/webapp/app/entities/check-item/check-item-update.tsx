import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTextData } from 'app/entities/text-data/text-data.reducer';
import { getEntities as getCheckLists } from 'app/entities/check-list/check-list.reducer';
import { CheckItemState } from 'app/shared/model/enumerations/check-item-state.model';
import { createEntity, getEntity, reset, updateEntity } from './check-item.reducer';

export const CheckItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const textData = useAppSelector(state => state.textData.entities);
  const checkLists = useAppSelector(state => state.checkList.entities);
  const checkItemEntity = useAppSelector(state => state.checkItem.entity);
  const loading = useAppSelector(state => state.checkItem.loading);
  const updating = useAppSelector(state => state.checkItem.updating);
  const updateSuccess = useAppSelector(state => state.checkItem.updateSuccess);
  const checkItemStateValues = Object.keys(CheckItemState);

  const handleClose = () => {
    navigate('/check-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTextData({}));
    dispatch(getCheckLists({}));
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
      ...checkItemEntity,
      ...values,
      data: textData.find(it => it.id.toString() === values.data?.toString()),
      checkList: checkLists.find(it => it.id.toString() === values.checkList?.toString()),
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
          state: 'INCOMPLETE',
          ...checkItemEntity,
          data: checkItemEntity?.data?.id,
          checkList: checkItemEntity?.checkList?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.checkItem.home.createOrEditLabel" data-cy="CheckItemCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.checkItem.home.createOrEditLabel">Create or edit a CheckItem</Translate>
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
                  id="check-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.checkItem.checkItemId')}
                id="check-item-checkItemId"
                name="checkItemId"
                data-cy="checkItemId"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.checkItem.name')}
                id="check-item-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.checkItem.state')}
                id="check-item-state"
                name="state"
                data-cy="state"
                type="select"
              >
                {checkItemStateValues.map(checkItemState => (
                  <option value={checkItemState} key={checkItemState}>
                    {translate(`trilloIntegrationApp.CheckItemState.${checkItemState}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="check-item-data"
                name="data"
                data-cy="data"
                label={translate('trilloIntegrationApp.checkItem.data')}
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
              <ValidatedField
                id="check-item-checkList"
                name="checkList"
                data-cy="checkList"
                label={translate('trilloIntegrationApp.checkItem.checkList')}
                type="select"
              >
                <option value="" key="0" />
                {checkLists
                  ? checkLists.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/check-item" replace color="info">
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

export default CheckItemUpdate;
