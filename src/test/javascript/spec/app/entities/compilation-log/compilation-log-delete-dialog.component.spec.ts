import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationLogDeleteDialogComponent } from 'app/entities/compilation-log/compilation-log-delete-dialog.component';
import { CompilationLogService } from 'app/entities/compilation-log/compilation-log.service';

describe('Component Tests', () => {
  describe('CompilationLog Management Delete Component', () => {
    let comp: CompilationLogDeleteDialogComponent;
    let fixture: ComponentFixture<CompilationLogDeleteDialogComponent>;
    let service: CompilationLogService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationLogDeleteDialogComponent]
      })
        .overrideTemplate(CompilationLogDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompilationLogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilationLogService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
