import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompilationLog } from 'app/shared/model/compilation-log.model';
import { CompilationLogService } from './compilation-log.service';

@Component({
  selector: 'jhi-compilation-log-delete-dialog',
  templateUrl: './compilation-log-delete-dialog.component.html'
})
export class CompilationLogDeleteDialogComponent {
  compilationLog: ICompilationLog;

  constructor(
    protected compilationLogService: CompilationLogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compilationLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compilationLogListModification',
        content: 'Deleted an compilationLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compilation-log-delete-popup',
  template: ''
})
export class CompilationLogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compilationLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompilationLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compilationLog = compilationLog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compilation-log', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compilation-log', { outlets: { popup: null } }]);
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
