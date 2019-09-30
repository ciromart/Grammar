import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompilationFile } from 'app/shared/model/compilation-file.model';

type EntityResponseType = HttpResponse<ICompilationFile>;
type EntityArrayResponseType = HttpResponse<ICompilationFile[]>;

@Injectable({ providedIn: 'root' })
export class CompilationFileService {
  public resourceUrl = SERVER_API_URL + 'api/compilation-files';

  constructor(protected http: HttpClient) {}

  create(compilationFile: ICompilationFile): Observable<EntityResponseType> {
    return this.http.post<ICompilationFile>(this.resourceUrl, compilationFile, { observe: 'response' });
  }

  update(compilationFile: ICompilationFile): Observable<EntityResponseType> {
    return this.http.put<ICompilationFile>(this.resourceUrl, compilationFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompilationFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompilationFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
