import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationLogComponent } from 'app/entities/compilation-log/compilation-log.component';
import { CompilationLogService } from 'app/entities/compilation-log/compilation-log.service';
import { CompilationLog } from 'app/shared/model/compilation-log.model';

describe('Component Tests', () => {
  describe('CompilationLog Management Component', () => {
    let comp: CompilationLogComponent;
    let fixture: ComponentFixture<CompilationLogComponent>;
    let service: CompilationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationLogComponent],
        providers: []
      })
        .overrideTemplate(CompilationLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilationLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilationLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompilationLog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.compilationLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
