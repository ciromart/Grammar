import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { CompilationFileDetailComponent } from 'app/entities/compilation-file/compilation-file-detail.component';
import { CompilationFile } from 'app/shared/model/compilation-file.model';

describe('Component Tests', () => {
  describe('CompilationFile Management Detail Component', () => {
    let comp: CompilationFileDetailComponent;
    let fixture: ComponentFixture<CompilationFileDetailComponent>;
    const route = ({ data: of({ compilationFile: new CompilationFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [CompilationFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompilationFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompilationFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compilationFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
