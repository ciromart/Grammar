import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationLogDetailComponent } from 'app/entities/compilation-log/compilation-log-detail.component';
import { CompilationLog } from 'app/shared/model/compilation-log.model';

describe('Component Tests', () => {
  describe('CompilationLog Management Detail Component', () => {
    let comp: CompilationLogDetailComponent;
    let fixture: ComponentFixture<CompilationLogDetailComponent>;
    const route = ({ data: of({ compilationLog: new CompilationLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompilationLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompilationLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compilationLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
