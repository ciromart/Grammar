import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompilationLog } from 'app/shared/model/compilation-log.model';

type EntityResponseType = HttpResponse<ICompilationLog>;
type EntityArrayResponseType = HttpResponse<ICompilationLog[]>;

@Injectable({ providedIn: 'root' })
export class CompilationLogService {
  public resourceUrl = SERVER_API_URL + 'api/compilation-logs';

  constructor(protected http: HttpClient) {}

  create(compilationLog: ICompilationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compilationLog);
    return this.http
      .post<ICompilationLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compilationLog: ICompilationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compilationLog);
    return this.http
      .put<ICompilationLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompilationLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompilationLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compilationLog: ICompilationLog): ICompilationLog {
    const copy: ICompilationLog = Object.assign({}, compilationLog, {
      insertTs: compilationLog.insertTs != null && compilationLog.insertTs.isValid() ? compilationLog.insertTs.toJSON() : null,
      lastUpdatTs: compilationLog.lastUpdatTs != null && compilationLog.lastUpdatTs.isValid() ? compilationLog.lastUpdatTs.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.insertTs = res.body.insertTs != null ? moment(res.body.insertTs) : null;
      res.body.lastUpdatTs = res.body.lastUpdatTs != null ? moment(res.body.lastUpdatTs) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((compilationLog: ICompilationLog) => {
        compilationLog.insertTs = compilationLog.insertTs != null ? moment(compilationLog.insertTs) : null;
        compilationLog.lastUpdatTs = compilationLog.lastUpdatTs != null ? moment(compilationLog.lastUpdatTs) : null;
      });
    }
    return res;
  }
}
