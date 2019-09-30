import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrammarEditorSharedModule } from 'app/shared/shared.module';
import { LmTemplateComponent } from './lm-template.component';
import { LmTemplateDetailComponent } from './lm-template-detail.component';
import { LmTemplateUpdateComponent } from './lm-template-update.component';
import { LmTemplateDeletePopupComponent, LmTemplateDeleteDialogComponent } from './lm-template-delete-dialog.component';
import { lmTemplateRoute, lmTemplatePopupRoute } from './lm-template.route';

const ENTITY_STATES = [...lmTemplateRoute, ...lmTemplatePopupRoute];

@NgModule({
  imports: [GrammarEditorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LmTemplateComponent,
    LmTemplateDetailComponent,
    LmTemplateUpdateComponent,
    LmTemplateDeleteDialogComponent,
    LmTemplateDeletePopupComponent
  ],
  entryComponents: [LmTemplateDeleteDialogComponent]
})
export class GrammarEditorLmTemplateModule {}
