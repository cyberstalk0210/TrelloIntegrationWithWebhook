import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getLabels } from 'app/entities/label/label.reducer';
import { getEntities as getBoards } from 'app/entities/board/board.reducer';
import { getEntities as getBoardLists } from 'app/entities/board-list/board-list.reducer';
import { createEntity, getEntity, reset, updateEntity } from './card.reducer';

export const CardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const labels = useAppSelector(state => state.label.entities);
  const boards = useAppSelector(state => state.board.entities);
  const boardLists = useAppSelector(state => state.boardList.entities);
  const cardEntity = useAppSelector(state => state.card.entity);
  const loading = useAppSelector(state => state.card.loading);
  const updating = useAppSelector(state => state.card.updating);
  const updateSuccess = useAppSelector(state => state.card.updateSuccess);

  const handleClose = () => {
    navigate(`/card${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLabels({}));
    dispatch(getBoards({}));
    dispatch(getBoardLists({}));
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
    if (values.position !== undefined && typeof values.position !== 'number') {
      values.position = Number(values.position);
    }
    values.dueDate = convertDateTimeToServer(values.dueDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...cardEntity,
      ...values,
      labels: mapIdList(values.labels),
      board: boards.find(it => it.id.toString() === values.board?.toString()),
      boardList: boardLists.find(it => it.id.toString() === values.boardList?.toString()),
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
          dueDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          ...cardEntity,
          dueDate: convertDateTimeFromServer(cardEntity.dueDate),
          createdAt: convertDateTimeFromServer(cardEntity.createdAt),
          updatedAt: convertDateTimeFromServer(cardEntity.updatedAt),
          labels: cardEntity?.labels?.map(e => e.id.toString()),
          board: cardEntity?.board?.id,
          boardList: cardEntity?.boardList?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.card.home.createOrEditLabel" data-cy="CardCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.card.home.createOrEditLabel">Create or edit a Card</Translate>
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
                  id="card-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.card.title')}
                id="card-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.description')}
                id="card-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.position')}
                id="card-position"
                name="position"
                data-cy="position"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.dueDate')}
                id="card-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.isArchived')}
                id="card-isArchived"
                name="isArchived"
                data-cy="isArchived"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.trelloId')}
                id="card-trelloId"
                name="trelloId"
                data-cy="trelloId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.createdAt')}
                id="card-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.updatedAt')}
                id="card-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.card.labels')}
                id="card-labels"
                data-cy="labels"
                type="select"
                multiple
                name="labels"
              >
                <option value="" key="0" />
                {labels
                  ? labels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="card-board"
                name="board"
                data-cy="board"
                label={translate('trilloIntegrationApp.card.board')}
                type="select"
              >
                <option value="" key="0" />
                {boards
                  ? boards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="card-boardList"
                name="boardList"
                data-cy="boardList"
                label={translate('trilloIntegrationApp.card.boardList')}
                type="select"
              >
                <option value="" key="0" />
                {boardLists
                  ? boardLists.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/card" replace color="info">
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

export default CardUpdate;
