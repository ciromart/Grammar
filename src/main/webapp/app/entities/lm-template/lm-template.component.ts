import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILmTemplate } from 'app/shared/model/lm-template.model';
import { AccountService } from 'app/core/auth/account.service';
import { LmTemplateService } from './lm-template.service';

@Component({
  selector: 'jhi-lm-template',
  templateUrl: './lm-template.component.html'
})
export class LmTemplateComponent implements OnInit, OnDestroy {
  lmTemplates: ILmTemplate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lmTemplateService: LmTemplateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.lmTemplateService
      .query()
      .pipe(
        filter((res: HttpResponse<ILmTemplate[]>) => res.ok),
        map((res: HttpResponse<ILmTemplate[]>) => res.body)
      )
      .subscribe(
        (res: ILmTemplate[]) => {
          this.lmTemplates = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLmTemplates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILmTemplate) {
    return item.id;
  }

  registerChangeInLmTemplates() {
    this.eventSubscriber = this.eventManager.subscribe('lmTemplateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
