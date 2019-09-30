import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModel } from 'app/shared/model/model.model';

type EntityResponseType = HttpResponse<IModel>;
type EntityArrayResponseType = HttpResponse<IModel[]>;

@Injectable({ providedIn: 'root' })
export class ModelService {
  public resourceUrl = SERVER_API_URL + 'api/models';

  constructor(protected http: HttpClient) {}

  create(model: IModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(model);
    return this.http
      .post<IModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(model: IModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(model);
    return this.http
      .put<IModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(model: IModel): IModel {
    const copy: IModel = Object.assign({}, model, {
      insertTs: model.insertTs != null && model.insertTs.isValid() ? model.insertTs.toJSON() : null,
      lastUpdateTs: model.lastUpdateTs != null && model.lastUpdateTs.isValid() ? model.lastUpdateTs.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.insertTs = res.body.insertTs != null ? moment(res.body.insertTs) : null;
      res.body.lastUpdateTs = res.body.lastUpdateTs != null ? moment(res.body.lastUpdateTs) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((model: IModel) => {
        model.insertTs = model.insertTs != null ? moment(model.insertTs) : null;
        model.lastUpdateTs = model.lastUpdateTs != null ? moment(model.lastUpdateTs) : null;
      });
    }
    return res;
  }
}
