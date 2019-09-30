import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationFileComponent } from 'app/entities/compilation-file/compilation-file.component';
import { CompilationFileService } from 'app/entities/compilation-file/compilation-file.service';
import { CompilationFile } from 'app/shared/model/compilation-file.model';

describe('Component Tests', () => {
  describe('CompilationFile Management Component', () => {
    let comp: CompilationFileComponent;
    let fixture: ComponentFixture<CompilationFileComponent>;
    let service: CompilationFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationFileComponent],
        providers: []
      })
        .overrideTemplate(CompilationFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilationFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilationFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompilationFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.compilationFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
