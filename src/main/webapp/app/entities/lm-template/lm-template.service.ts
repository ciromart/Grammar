import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILmTemplate } from 'app/shared/model/lm-template.model';

type EntityResponseType = HttpResponse<ILmTemplate>;
type EntityArrayResponseType = HttpResponse<ILmTemplate[]>;

@Injectable({ providedIn: 'root' })
export class LmTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/lm-templates';

  constructor(protected http: HttpClient) {}

  create(lmTemplate: ILmTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lmTemplate);
    return this.http
      .post<ILmTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lmTemplate: ILmTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lmTemplate);
    return this.http
      .put<ILmTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILmTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILmTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lmTemplate: ILmTemplate): ILmTemplate {
    const copy: ILmTemplate = Object.assign({}, lmTemplate, {
      insertTs: lmTemplate.insertTs != null && lmTemplate.insertTs.isValid() ? lmTemplate.insertTs.toJSON() : null,
      lastUpdateTs: lmTemplate.lastUpdateTs != null && lmTemplate.lastUpdateTs.isValid() ? lmTemplate.lastUpdateTs.toJSON() : null
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
      res.body.forEach((lmTemplate: ILmTemplate) => {
        lmTemplate.insertTs = lmTemplate.insertTs != null ? moment(lmTemplate.insertTs) : null;
        lmTemplate.lastUpdateTs = lmTemplate.lastUpdateTs != null ? moment(lmTemplate.lastUpdateTs) : null;
      });
    }
    return res;
  }
}
