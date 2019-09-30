import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompilationLog } from 'app/shared/model/compilation-log.model';
import { CompilationLogService } from './compilation-log.service';
import { CompilationLogComponent } from './compilation-log.component';
import { CompilationLogDetailComponent } from './compilation-log-detail.component';
import { CompilationLogUpdateComponent } from './compilation-log-update.component';
import { CompilationLogDeletePopupComponent } from './compilation-log-delete-dialog.component';
import { ICompilationLog } from 'app/shared/model/compilation-log.model';

@Injectable({ providedIn: 'root' })
export class CompilationLogResolve implements Resolve<ICompilationLog> {
  constructor(private service: CompilationLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompilationLog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompilationLog>) => response.ok),
        map((compilationLog: HttpResponse<CompilationLog>) => compilationLog.body)
      );
    }
    return of(new CompilationLog());
  }
}

export const compilationLogRoute: Routes = [
  {
    path: '',
    component: CompilationLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompilationLogDetailComponent,
    resolve: {
      compilationLog: CompilationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompilationLogUpdateComponent,
    resolve: {
      compilationLog: CompilationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompilationLogUpdateComponent,
    resolve: {
      compilationLog: CompilationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compilationLogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompilationLogDeletePopupComponent,
    resolve: {
      compilationLog: CompilationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
