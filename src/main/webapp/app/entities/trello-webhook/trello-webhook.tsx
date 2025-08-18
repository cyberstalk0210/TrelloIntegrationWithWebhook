import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './trello-webhook.reducer';

export const TrelloWebhook = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const trelloWebhookList = useAppSelector(state => state.trelloWebhook.entities);
  const loading = useAppSelector(state => state.trelloWebhook.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="trello-webhook-heading" data-cy="TrelloWebhookHeading">
        <Translate contentKey="trilloIntegrationApp.trelloWebhook.home.title">Trello Webhooks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="trilloIntegrationApp.trelloWebhook.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/trello-webhook/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="trilloIntegrationApp.trelloWebhook.home.createLabel">Create new Trello Webhook</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trelloWebhookList && trelloWebhookList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('trelloWebhookId')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.trelloWebhookId">Trello Webhook Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('trelloWebhookId')} />
                </th>
                <th className="hand" onClick={sort('idModel')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.idModel">Id Model</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('idModel')} />
                </th>
                <th className="hand" onClick={sort('callbackUrl')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.callbackUrl">Callback Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('callbackUrl')} />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.active">Active</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('active')} />
                </th>
                <th className="hand" onClick={sort('lastReceivedAt')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.lastReceivedAt">Last Received At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastReceivedAt')} />
                </th>
                <th className="hand" onClick={sort('secret')}>
                  <Translate contentKey="trilloIntegrationApp.trelloWebhook.secret">Secret</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('secret')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trelloWebhookList.map((trelloWebhook, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/trello-webhook/${trelloWebhook.id}`} color="link" size="sm">
                      {trelloWebhook.id}
                    </Button>
                  </td>
                  <td>{trelloWebhook.trelloWebhookId}</td>
                  <td>{trelloWebhook.idModel}</td>
                  <td>{trelloWebhook.callbackUrl}</td>
                  <td>{trelloWebhook.active ? 'true' : 'false'}</td>
                  <td>
                    {trelloWebhook.lastReceivedAt ? (
                      <TextFormat type="date" value={trelloWebhook.lastReceivedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{trelloWebhook.secret}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/trello-webhook/${trelloWebhook.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/trello-webhook/${trelloWebhook.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/trello-webhook/${trelloWebhook.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="trilloIntegrationApp.trelloWebhook.home.notFound">No Trello Webhooks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TrelloWebhook;
