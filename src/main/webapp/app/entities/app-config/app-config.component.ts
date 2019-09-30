import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAppConfig } from 'app/shared/model/app-config.model';
import { AccountService } from 'app/core/auth/account.service';
import { AppConfigService } from './app-config.service';

@Component({
  selector: 'jhi-app-config',
  templateUrl: './app-config.component.html'
})
export class AppConfigComponent implements OnInit, OnDestroy {
  appConfigs: IAppConfig[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected appConfigService: AppConfigService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.appConfigService
      .query()
      .pipe(
        filter((res: HttpResponse<IAppConfig[]>) => res.ok),
        map((res: HttpResponse<IAppConfig[]>) => res.body)
      )
      .subscribe(
        (res: IAppConfig[]) => {
          this.appConfigs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAppConfigs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAppConfig) {
    return item.id;
  }

  registerChangeInAppConfigs() {
    this.eventSubscriber = this.eventManager.subscribe('appConfigListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
