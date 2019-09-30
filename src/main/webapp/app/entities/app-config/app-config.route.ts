import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppConfig } from 'app/shared/model/app-config.model';
import { AppConfigService } from './app-config.service';
import { AppConfigComponent } from './app-config.component';
import { AppConfigDetailComponent } from './app-config-detail.component';
import { AppConfigUpdateComponent } from './app-config-update.component';
import { AppConfigDeletePopupComponent } from './app-config-delete-dialog.component';
import { IAppConfig } from 'app/shared/model/app-config.model';

@Injectable({ providedIn: 'root' })
export class AppConfigResolve implements Resolve<IAppConfig> {
  constructor(private service: AppConfigService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppConfig> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AppConfig>) => response.ok),
        map((appConfig: HttpResponse<AppConfig>) => appConfig.body)
      );
    }
    return of(new AppConfig());
  }
}

export const appConfigRoute: Routes = [
  {
    path: '',
    component: AppConfigComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.appConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppConfigDetailComponent,
    resolve: {
      appConfig: AppConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.appConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppConfigUpdateComponent,
    resolve: {
      appConfig: AppConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.appConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppConfigUpdateComponent,
    resolve: {
      appConfig: AppConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.appConfig.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appConfigPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppConfigDeletePopupComponent,
    resolve: {
      appConfig: AppConfigResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.appConfig.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
