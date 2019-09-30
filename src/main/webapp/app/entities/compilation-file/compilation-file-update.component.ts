import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompilationFile, CompilationFile } from 'app/shared/model/compilation-file.model';
import { CompilationFileService } from './compilation-file.service';
import { ICompilationLog } from 'app/shared/model/compilation-log.model';
import { CompilationLogService } from 'app/entities/compilation-log/compilation-log.service';

@Component({
  selector: 'jhi-compilation-file-update',
  templateUrl: './compilation-file-update.component.html'
})
export class CompilationFileUpdateComponent implements OnInit {
  isSaving: boolean;

  compilationlogs: ICompilationLog[];

  editForm = this.fb.group({
    id: [],
    path: [],
    compilationLog: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compilationFileService: CompilationFileService,
    protected compilationLogService: CompilationLogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compilationFile }) => {
      this.updateForm(compilationFile);
    });
    this.compilationLogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompilationLog[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompilationLog[]>) => response.body)
      )
      .subscribe((res: ICompilationLog[]) => (this.compilationlogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compilationFile: ICompilationFile) {
    this.editForm.patchValue({
      id: compilationFile.id,
      path: compilationFile.path,
      compilationLog: compilationFile.compilationLog
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compilationFile = this.createFromForm();
    if (compilationFile.id !== undefined) {
      this.subscribeToSaveResponse(this.compilationFileService.update(compilationFile));
    } else {
      this.subscribeToSaveResponse(this.compilationFileService.create(compilationFile));
    }
  }

  private createFromForm(): ICompilationFile {
    return {
      ...new CompilationFile(),
      id: this.editForm.get(['id']).value,
      path: this.editForm.get(['path']).value,
      compilationLog: this.editForm.get(['compilationLog']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompilationFile>>) {
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

  trackCompilationLogById(index: number, item: ICompilationLog) {
    return item.id;
  }
}
