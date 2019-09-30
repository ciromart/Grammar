import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAppConfig, AppConfig } from 'app/shared/model/app-config.model';
import { AppConfigService } from './app-config.service';

@Component({
  selector: 'jhi-app-config-update',
  templateUrl: './app-config-update.component.html'
})
export class AppConfigUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    criticalWordsMaxFileSize: [],
    criticalWordsMaxWords: [],
    additionalContextMaxFileSize: [],
    additionalContextMaxFileWords: [],
    minOccurencyContext: [],
    windowsMaxWords: []
  });

  constructor(protected appConfigService: AppConfigService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appConfig }) => {
      this.updateForm(appConfig);
    });
  }

  updateForm(appConfig: IAppConfig) {
    this.editForm.patchValue({
      id: appConfig.id,
      criticalWordsMaxFileSize: appConfig.criticalWordsMaxFileSize,
      criticalWordsMaxWords: appConfig.criticalWordsMaxWords,
      additionalContextMaxFileSize: appConfig.additionalContextMaxFileSize,
      additionalContextMaxFileWords: appConfig.additionalContextMaxFileWords,
      minOccurencyContext: appConfig.minOccurencyContext,
      windowsMaxWords: appConfig.windowsMaxWords
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
      windowsMaxWords: this.editForm.get(['windowsMaxWords']).value
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
}
