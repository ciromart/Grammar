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
import { ICompilationLog, CompilationLog } from 'app/shared/model/compilation-log.model';
import { CompilationLogService } from './compilation-log.service';
import { IModel } from 'app/shared/model/model.model';
import { ModelService } from 'app/entities/model/model.service';

@Component({
  selector: 'jhi-compilation-log-update',
  templateUrl: './compilation-log-update.component.html'
})
export class CompilationLogUpdateComponent implements OnInit {
  isSaving: boolean;

  models: IModel[];

  editForm = this.fb.group({
    id: [],
    insertTs: [],
    lastUpdatTs: [],
    status: [],
    rpkLink: [],
    model: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compilationLogService: CompilationLogService,
    protected modelService: ModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compilationLog }) => {
      this.updateForm(compilationLog);
    });
    this.modelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModel[]>) => response.body)
      )
      .subscribe((res: IModel[]) => (this.models = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compilationLog: ICompilationLog) {
    this.editForm.patchValue({
      id: compilationLog.id,
      insertTs: compilationLog.insertTs != null ? compilationLog.insertTs.format(DATE_TIME_FORMAT) : null,
      lastUpdatTs: compilationLog.lastUpdatTs != null ? compilationLog.lastUpdatTs.format(DATE_TIME_FORMAT) : null,
      status: compilationLog.status,
      rpkLink: compilationLog.rpkLink,
      model: compilationLog.model
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compilationLog = this.createFromForm();
    if (compilationLog.id !== undefined) {
      this.subscribeToSaveResponse(this.compilationLogService.update(compilationLog));
    } else {
      this.subscribeToSaveResponse(this.compilationLogService.create(compilationLog));
    }
  }

  private createFromForm(): ICompilationLog {
    return {
      ...new CompilationLog(),
      id: this.editForm.get(['id']).value,
      insertTs: this.editForm.get(['insertTs']).value != null ? moment(this.editForm.get(['insertTs']).value, DATE_TIME_FORMAT) : undefined,
      lastUpdatTs:
        this.editForm.get(['lastUpdatTs']).value != null ? moment(this.editForm.get(['lastUpdatTs']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      rpkLink: this.editForm.get(['rpkLink']).value,
      model: this.editForm.get(['model']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompilationLog>>) {
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

  trackModelById(index: number, item: IModel) {
    return item.id;
  }
}
