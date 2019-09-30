import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILmTemplate, LmTemplate } from 'app/shared/model/lm-template.model';
import { LmTemplateService } from './lm-template.service';

@Component({
  selector: 'jhi-lm-template-update',
  templateUrl: './lm-template-update.component.html'
})
export class LmTemplateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    langCode: [],
    countryCode: [],
    lmStandardCode: [],
    path: [],
    insertTs: [],
    lastUpdateTs: []
  });

  constructor(protected lmTemplateService: LmTemplateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lmTemplate }) => {
      this.updateForm(lmTemplate);
    });
  }

  updateForm(lmTemplate: ILmTemplate) {
    this.editForm.patchValue({
      id: lmTemplate.id,
      name: lmTemplate.name,
      langCode: lmTemplate.langCode,
      countryCode: lmTemplate.countryCode,
      lmStandardCode: lmTemplate.lmStandardCode,
      path: lmTemplate.path,
      insertTs: lmTemplate.insertTs != null ? lmTemplate.insertTs.format(DATE_TIME_FORMAT) : null,
      lastUpdateTs: lmTemplate.lastUpdateTs != null ? lmTemplate.lastUpdateTs.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lmTemplate = this.createFromForm();
    if (lmTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.lmTemplateService.update(lmTemplate));
    } else {
      this.subscribeToSaveResponse(this.lmTemplateService.create(lmTemplate));
    }
  }

  private createFromForm(): ILmTemplate {
    return {
      ...new LmTemplate(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      langCode: this.editForm.get(['langCode']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      lmStandardCode: this.editForm.get(['lmStandardCode']).value,
      path: this.editForm.get(['path']).value,
      insertTs: this.editForm.get(['insertTs']).value != null ? moment(this.editForm.get(['insertTs']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdateTs:
        this.editForm.get(['lastUpdateTs']).value != null ? moment(this.editForm.get(['lastUpdateTs']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILmTemplate>>) {
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
