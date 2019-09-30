import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationFileUpdateComponent } from 'app/entities/compilation-file/compilation-file-update.component';
import { CompilationFileService } from 'app/entities/compilation-file/compilation-file.service';
import { CompilationFile } from 'app/shared/model/compilation-file.model';

describe('Component Tests', () => {
  describe('CompilationFile Management Update Component', () => {
    let comp: CompilationFileUpdateComponent;
    let fixture: ComponentFixture<CompilationFileUpdateComponent>;
    let service: CompilationFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompilationFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilationFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilationFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompilationFile(123);
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
        const entity = new CompilationFile();
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
