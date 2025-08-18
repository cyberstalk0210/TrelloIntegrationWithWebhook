import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBoards } from 'app/entities/board/board.reducer';
import { createEntity, getEntity, reset, updateEntity } from './board-list.reducer';

export const BoardListUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const boards = useAppSelector(state => state.board.entities);
  const boardListEntity = useAppSelector(state => state.boardList.entity);
  const loading = useAppSelector(state => state.boardList.loading);
  const updating = useAppSelector(state => state.boardList.updating);
  const updateSuccess = useAppSelector(state => state.boardList.updateSuccess);

  const handleClose = () => {
    navigate('/board-list');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBoards({}));
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

    const entity = {
      ...boardListEntity,
      ...values,
      board: boards.find(it => it.id.toString() === values.board?.toString()),
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
          ...boardListEntity,
          board: boardListEntity?.board?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="trilloIntegrationApp.boardList.home.createOrEditLabel" data-cy="BoardListCreateUpdateHeading">
            <Translate contentKey="trilloIntegrationApp.boardList.home.createOrEditLabel">Create or edit a BoardList</Translate>
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
                  id="board-list-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('trilloIntegrationApp.boardList.name')}
                id="board-list-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.boardList.position')}
                id="board-list-position"
                name="position"
                data-cy="position"
                type="text"
              />
              <ValidatedField
                label={translate('trilloIntegrationApp.boardList.trelloId')}
                id="board-list-trelloId"
                name="trelloId"
                data-cy="trelloId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="board-list-board"
                name="board"
                data-cy="board"
                label={translate('trilloIntegrationApp.boardList.board')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/board-list" replace color="info">
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

export default BoardListUpdate;
