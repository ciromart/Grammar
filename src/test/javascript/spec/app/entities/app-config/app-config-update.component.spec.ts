import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { AppConfigUpdateComponent } from 'app/entities/app-config/app-config-update.component';
import { AppConfigService } from 'app/entities/app-config/app-config.service';
import { AppConfig } from 'app/shared/model/app-config.model';

describe('Component Tests', () => {
  describe('AppConfig Management Update Component', () => {
    let comp: AppConfigUpdateComponent;
    let fixture: ComponentFixture<AppConfigUpdateComponent>;
    let service: AppConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [AppConfigUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppConfig(123);
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
        const entity = new AppConfig();
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
