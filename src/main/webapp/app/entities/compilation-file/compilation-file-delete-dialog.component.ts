import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompilationFile } from 'app/shared/model/compilation-file.model';
import { CompilationFileService } from './compilation-file.service';

@Component({
  selector: 'jhi-compilation-file-delete-dialog',
  templateUrl: './compilation-file-delete-dialog.component.html'
})
export class CompilationFileDeleteDialogComponent {
  compilationFile: ICompilationFile;

  constructor(
    protected compilationFileService: CompilationFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compilationFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compilationFileListModification',
        content: 'Deleted an compilationFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compilation-file-delete-popup',
  template: ''
})
export class CompilationFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compilationFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompilationFileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compilationFile = compilationFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compilation-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compilation-file', { outlets: { popup: null } }]);
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
