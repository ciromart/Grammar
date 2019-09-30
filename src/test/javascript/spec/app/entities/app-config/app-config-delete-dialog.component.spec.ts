import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GrammarEditorTestModule } from '../../../test.module';
import { AppConfigDeleteDialogComponent } from 'app/entities/app-config/app-config-delete-dialog.component';
import { AppConfigService } from 'app/entities/app-config/app-config.service';

describe('Component Tests', () => {
  describe('AppConfig Management Delete Component', () => {
    let comp: AppConfigDeleteDialogComponent;
    let fixture: ComponentFixture<AppConfigDeleteDialogComponent>;
    let service: AppConfigService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [AppConfigDeleteDialogComponent]
      })
        .overrideTemplate(AppConfigDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppConfigDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppConfigService);
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
