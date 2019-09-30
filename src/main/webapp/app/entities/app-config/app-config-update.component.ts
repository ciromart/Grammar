import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppConfig, AppConfig } from 'app/shared/model/app-config.model';
import { AppConfigService } from './app-config.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-app-config-update',
  templateUrl: './app-config-update.component.html'
})
export class AppConfigUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    criticalWordsMaxFileSize: [],
    criticalWordsMaxWords: [],
    additionalContextMaxFileSize: [],
    additionalContextMaxFileWords: [],
    minOccurencyContext: [],
    windowsMaxWords: [],
    users: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected appConfigService: AppConfigService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appConfig }) => {
      this.updateForm(appConfig);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(appConfig: IAppConfig) {
    this.editForm.patchValue({
      id: appConfig.id,
      criticalWordsMaxFileSize: appConfig.criticalWordsMaxFileSize,
      criticalWordsMaxWords: appConfig.criticalWordsMaxWords,
      additionalContextMaxFileSize: appConfig.additionalContextMaxFileSize,
      additionalContextMaxFileWords: appConfig.additionalContextMaxFileWords,
      minOccurencyContext: appConfig.minOccurencyContext,
      windowsMaxWords: appConfig.windowsMaxWords,
      users: appConfig.users
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appConfig = this.createFromForm();
    if (appConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.appConfigService.update(appConfig));
    } else {
      this.subscribeToSaveResponse(this.appConfigService.create(appConfig));
    }
  }

  private createFromForm(): IAppConfig {
    return {
      ...new AppConfig(),
      id: this.editForm.get(['id']).value,
      criticalWordsMaxFileSize: this.editForm.get(['criticalWordsMaxFileSize']).value,
      criticalWordsMaxWords: this.editForm.get(['criticalWordsMaxWords']).value,
      additionalContextMaxFileSize: this.editForm.get(['additionalContextMaxFileSize']).value,
      additionalContextMaxFileWords: this.editForm.get(['additionalContextMaxFileWords']).value,
      minOccurencyContext: this.editForm.get(['minOccurencyContext']).value,
      windowsMaxWords: this.editForm.get(['windowsMaxWords']).value,
      users: this.editForm.get(['users']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppConfig>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
