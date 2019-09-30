import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompilationFile } from 'app/shared/model/compilation-file.model';
import { AccountService } from 'app/core/auth/account.service';
import { CompilationFileService } from './compilation-file.service';

@Component({
  selector: 'jhi-compilation-file',
  templateUrl: './compilation-file.component.html'
})
export class CompilationFileComponent implements OnInit, OnDestroy {
  compilationFiles: ICompilationFile[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected compilationFileService: CompilationFileService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.compilationFileService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompilationFile[]>) => res.ok),
        map((res: HttpResponse<ICompilationFile[]>) => res.body)
      )
      .subscribe(
        (res: ICompilationFile[]) => {
          this.compilationFiles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompilationFiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompilationFile) {
    return item.id;
  }

  registerChangeInCompilationFiles() {
    this.eventSubscriber = this.eventManager.subscribe('compilationFileListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
