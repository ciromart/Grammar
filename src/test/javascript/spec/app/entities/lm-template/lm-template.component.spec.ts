import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrammarEditorTestModule } from '../../../test.module';
import { LmTemplateComponent } from 'app/entities/lm-template/lm-template.component';
import { LmTemplateService } from 'app/entities/lm-template/lm-template.service';
import { LmTemplate } from 'app/shared/model/lm-template.model';

describe('Component Tests', () => {
  describe('LmTemplate Management Component', () => {
    let comp: LmTemplateComponent;
    let fixture: ComponentFixture<LmTemplateComponent>;
    let service: LmTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [LmTemplateComponent],
        providers: []
      })
        .overrideTemplate(LmTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LmTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LmTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LmTemplate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lmTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
