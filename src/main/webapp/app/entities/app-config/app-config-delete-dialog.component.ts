import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppConfig } from 'app/shared/model/app-config.model';
import { AppConfigService } from './app-config.service';

@Component({
  selector: 'jhi-app-config-delete-dialog',
  templateUrl: './app-config-delete-dialog.component.html'
})
export class AppConfigDeleteDialogComponent {
  appConfig: IAppConfig;

  constructor(protected appConfigService: AppConfigService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appConfigService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appConfigListModification',
        content: 'Deleted an appConfig'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-app-config-delete-popup',
  template: ''
})
export class AppConfigDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appConfig }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppConfigDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appConfig = appConfig;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/app-config', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/app-config', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
