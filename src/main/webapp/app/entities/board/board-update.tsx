import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getWorkspaces } from 'app/entities/workspace/workspace.reducer';
import { BoardVisibility } from 'app/shared/model/enumerations/board-visibility.model';
import { createEntity, getEntity, reset, updateEntity } from './board.reducer';

export const BoardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workspaces = useAppSelector(state => state.workspace.entities);
  const boardEntity = useAppSelector(state => state.board.entity);
  const loading = useAppSelector(state => state.board.loading);
  const updating = useAppSelector(state => state.board.updating);
  const updateSuccess = useAppSelector(state => state.board.updateSuccess);
  const boardVisibilityValues = Object.keys(BoardVisibility);

  const handleClose = () => {
    navigate(`/board${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWorkspaces({}));
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
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...boardEntity,
      ...values,
      workspace: workspaces.find(it => it.id.toString() === values.workspace?.toString()),
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
          createdAt: displayDefaultDateTime(),
        }
      : {
          visibility: 'PRIVATE',
          ...boardEntity,
          createdAt: convertDateTimeFromServer(boardEntity.createdAt),
          workspace: boardEntity?.workspace?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.board.home.createOrEditLabel" data-cy="BoardCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.board.home.createOrEditLabel">Create or edit a Board</Translate>
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
                  id="board-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.board.title')}
                id="board-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.board.description')}
                id="board-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.board.visibility')}
                id="board-visibility"
                name="visibility"
                data-cy="visibility"
                type="select"
              >
                {boardVisibilityValues.map(boardVisibility => (
                  <option value={boardVisibility} key={boardVisibility}>
                    {translate(`trilloIntegrationApp.BoardVisibility.${boardVisibility}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('trilloIntegrationApp.board.trelloId')}
                id="board-trelloId"
                name="trelloId"
                data-cy="trelloId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.board.createdAt')}
                id="board-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.board.createdBy')}
                id="board-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="board-workspace"
                name="workspace"
                data-cy="workspace"
                label={translate('trilloIntegrationApp.board.workspace')}
                type="select"
              >
                <option value="" key="0" />
                {workspaces
                  ? workspaces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/board" replace color="info">
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

export default BoardUpdate;
