import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationLogUpdateComponent } from 'app/entities/compilation-log/compilation-log-update.component';
import { CompilationLogService } from 'app/entities/compilation-log/compilation-log.service';
import { CompilationLog } from 'app/shared/model/compilation-log.model';

describe('Component Tests', () => {
  describe('CompilationLog Management Update Component', () => {
    let comp: CompilationLogUpdateComponent;
    let fixture: ComponentFixture<CompilationLogUpdateComponent>;
    let service: CompilationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationLogUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompilationLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilationLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilationLogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompilationLog(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompilationLog();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
