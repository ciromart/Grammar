import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompilationLog } from 'app/shared/model/compilation-log.model';
import { AccountService } from 'app/core/auth/account.service';
import { CompilationLogService } from './compilation-log.service';

@Component({
  selector: 'jhi-compilation-log',
  templateUrl: './compilation-log.component.html'
})
export class CompilationLogComponent implements OnInit, OnDestroy {
  compilationLogs: ICompilationLog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected compilationLogService: CompilationLogService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.compilationLogService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompilationLog[]>) => res.ok),
        map((res: HttpResponse<ICompilationLog[]>) => res.body)
      )
      .subscribe(
        (res: ICompilationLog[]) => {
          this.compilationLogs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompilationLogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompilationLog) {
    return item.id;
  }

  registerChangeInCompilationLogs() {
    this.eventSubscriber = this.eventManager.subscribe('compilationLogListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
