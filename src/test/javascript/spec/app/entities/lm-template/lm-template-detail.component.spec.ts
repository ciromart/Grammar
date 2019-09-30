import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { LmTemplateDetailComponent } from 'app/entities/lm-template/lm-template-detail.component';
import { LmTemplate } from 'app/shared/model/lm-template.model';

describe('Component Tests', () => {
  describe('LmTemplate Management Detail Component', () => {
    let comp: LmTemplateDetailComponent;
    let fixture: ComponentFixture<LmTemplateDetailComponent>;
    const route = ({ data: of({ lmTemplate: new LmTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [LmTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LmTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LmTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lmTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
