import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GrammarEditorTestModule } from '../../../test.module';
import { LmTemplateUpdateComponent } from 'app/entities/lm-template/lm-template-update.component';
import { LmTemplateService } from 'app/entities/lm-template/lm-template.service';
import { LmTemplate } from 'app/shared/model/lm-template.model';

describe('Component Tests', () => {
  describe('LmTemplate Management Update Component', () => {
    let comp: LmTemplateUpdateComponent;
    let fixture: ComponentFixture<LmTemplateUpdateComponent>;
    let service: LmTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GrammarEditorTestModule],
        declarations: [LmTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LmTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LmTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LmTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LmTemplate(123);
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
        const entity = new LmTemplate();
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
