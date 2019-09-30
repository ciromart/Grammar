import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppConfig } from 'app/shared/model/app-config.model';

type EntityResponseType = HttpResponse<IAppConfig>;
type EntityArrayResponseType = HttpResponse<IAppConfig[]>;

@Injectable({ providedIn: 'root' })
export class AppConfigService {
  public resourceUrl = SERVER_API_URL + 'api/app-configs';

  constructor(protected http: HttpClient) {}

  create(appConfig: IAppConfig): Observable<EntityResponseType> {
    return this.http.post<IAppConfig>(this.resourceUrl, appConfig, { observe: 'response' });
  }

  update(appConfig: IAppConfig): Observable<EntityResponseType> {
    return this.http.put<IAppConfig>(this.resourceUrl, appConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
