import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LmTemplate } from 'app/shared/model/lm-template.model';
import { LmTemplateService } from './lm-template.service';
import { LmTemplateComponent } from './lm-template.component';
import { LmTemplateDetailComponent } from './lm-template-detail.component';
import { LmTemplateUpdateComponent } from './lm-template-update.component';
import { LmTemplateDeletePopupComponent } from './lm-template-delete-dialog.component';
import { ILmTemplate } from 'app/shared/model/lm-template.model';

@Injectable({ providedIn: 'root' })
export class LmTemplateResolve implements Resolve<ILmTemplate> {
  constructor(private service: LmTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILmTemplate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LmTemplate>) => response.ok),
        map((lmTemplate: HttpResponse<LmTemplate>) => lmTemplate.body)
      );
    }
    return of(new LmTemplate());
  }
}

export const lmTemplateRoute: Routes = [
  {
    path: '',
    component: LmTemplateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.lmTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LmTemplateDetailComponent,
    resolve: {
      lmTemplate: LmTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.lmTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LmTemplateUpdateComponent,
    resolve: {
      lmTemplate: LmTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.lmTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LmTemplateUpdateComponent,
    resolve: {
      lmTemplate: LmTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.lmTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lmTemplatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LmTemplateDeletePopupComponent,
    resolve: {
      lmTemplate: LmTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.lmTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
