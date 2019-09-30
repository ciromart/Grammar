import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { AppConfigDetailComponent } from 'app/entities/app-config/app-config-detail.component';
import { AppConfig } from 'app/shared/model/app-config.model';

describe('Component Tests', () => {
  describe('AppConfig Management Detail Component', () => {
    let comp: AppConfigDetailComponent;
    let fixture: ComponentFixture<AppConfigDetailComponent>;
    const route = ({ data: of({ appConfig: new AppConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [AppConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
