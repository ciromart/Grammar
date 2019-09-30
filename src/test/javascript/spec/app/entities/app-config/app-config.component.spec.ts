import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GrammarEditorTestModule } from '../../../test.module';
import { AppConfigComponent } from 'app/entities/app-config/app-config.component';
import { AppConfigService } from 'app/entities/app-config/app-config.service';
import { AppConfig } from 'app/shared/model/app-config.model';

describe('Component Tests', () => {
  describe('AppConfig Management Component', () => {
    let comp: AppConfigComponent;
    let fixture: ComponentFixture<AppConfigComponent>;
    let service: AppConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [AppConfigComponent],
        providers: []
      })
        .overrideTemplate(AppConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AppConfig(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.appConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
