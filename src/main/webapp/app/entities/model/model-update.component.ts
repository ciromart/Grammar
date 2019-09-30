import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IModel, Model } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILmTemplate } from 'app/shared/model/lm-template.model';
import { LmTemplateService } from 'app/entities/lm-template/lm-template.service';

@Component({
  selector: 'jhi-model-update',
  templateUrl: './model-update.component.html'
})
export class ModelUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  lmtemplates: ILmTemplate[];

  editForm = this.fb.group({
    id: [],
    name: [],
    mailNetworkName: [],
    insertTs: [],
    lastUpdateTs: [],
    activated: [],
    users: [],
    lmTemplate: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected modelService: ModelService,
    protected userService: UserService,
    protected lmTemplateService: LmTemplateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ model }) => {
      this.updateForm(model);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.lmTemplateService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILmTemplate[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILmTemplate[]>) => response.body)
      )
      .subscribe((res: ILmTemplate[]) => (this.lmtemplates = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(model: IModel) {
    this.editForm.patchValue({
      id: model.id,
      name: model.name,
      mailNetworkName: model.mailNetworkName,
      insertTs: model.insertTs != null ? model.insertTs.format(DATE_TIME_FORMAT) : null,
      lastUpdateTs: model.lastUpdateTs != null ? model.lastUpdateTs.format(DATE_TIME_FORMAT) : null,
      activated: model.activated,
      users: model.users,
      lmTemplate: model.lmTemplate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const model = this.createFromForm();
    if (model.id !== undefined) {
      this.subscribeToSaveResponse(this.modelService.update(model));
    } else {
      this.subscribeToSaveResponse(this.modelService.create(model));
    }
  }

  private createFromForm(): IModel {
    return {
      ...new Model(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      mailNetworkName: this.editForm.get(['mailNetworkName']).value,
      insertTs: this.editForm.get(['insertTs']).value != null ? moment(this.editForm.get(['insertTs']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdateTs:
        this.editForm.get(['lastUpdateTs']).value != null ? moment(this.editForm.get(['lastUpdateTs']).value, DATE_TIME_FORMAT) : undefined,
      activated: this.editForm.get(['activated']).value,
      users: this.editForm.get(['users']).value,
      lmTemplate: this.editForm.get(['lmTemplate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModel>>) {
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

  trackLmTemplateById(index: number, item: ILmTemplate) {
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
