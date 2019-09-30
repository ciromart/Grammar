import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILmTemplate } from 'app/shared/model/lm-template.model';
import { LmTemplateService } from './lm-template.service';

@Component({
  selector: 'jhi-lm-template-delete-dialog',
  templateUrl: './lm-template-delete-dialog.component.html'
})
export class LmTemplateDeleteDialogComponent {
  lmTemplate: ILmTemplate;

  constructor(
    protected lmTemplateService: LmTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lmTemplateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lmTemplateListModification',
        content: 'Deleted an lmTemplate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lm-template-delete-popup',
  template: ''
})
export class LmTemplateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lmTemplate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LmTemplateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lmTemplate = lmTemplate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/lm-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/lm-template', { outlets: { popup: null } }]);
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
