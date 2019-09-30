import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompilationFile } from 'app/shared/model/compilation-file.model';
import { CompilationFileService } from './compilation-file.service';
import { CompilationFileComponent } from './compilation-file.component';
import { CompilationFileDetailComponent } from './compilation-file-detail.component';
import { CompilationFileUpdateComponent } from './compilation-file-update.component';
import { CompilationFileDeletePopupComponent } from './compilation-file-delete-dialog.component';
import { ICompilationFile } from 'app/shared/model/compilation-file.model';

@Injectable({ providedIn: 'root' })
export class CompilationFileResolve implements Resolve<ICompilationFile> {
  constructor(private service: CompilationFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompilationFile> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompilationFile>) => response.ok),
        map((compilationFile: HttpResponse<CompilationFile>) => compilationFile.body)
      );
    }
    return of(new CompilationFile());
  }
}

export const compilationFileRoute: Routes = [
  {
    path: '',
    component: CompilationFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompilationFileDetailComponent,
    resolve: {
      compilationFile: CompilationFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompilationFileUpdateComponent,
    resolve: {
      compilationFile: CompilationFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompilationFileUpdateComponent,
    resolve: {
      compilationFile: CompilationFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compilationFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompilationFileDeletePopupComponent,
    resolve: {
      compilationFile: CompilationFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'grammarEditorApp.compilationFile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
