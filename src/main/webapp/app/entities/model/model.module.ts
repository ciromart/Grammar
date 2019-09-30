import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrammarEditorSharedModule } from 'app/shared/shared.module';
import { ModelComponent } from './model.component';
import { ModelDetailComponent } from './model-detail.component';
import { ModelUpdateComponent } from './model-update.component';
import { ModelDeletePopupComponent, ModelDeleteDialogComponent } from './model-delete-dialog.component';
import { modelRoute, modelPopupRoute } from './model.route';

const ENTITY_STATES = [...modelRoute, ...modelPopupRoute];

@NgModule({
  imports: [GrammarEditorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ModelComponent, ModelDetailComponent, ModelUpdateComponent, ModelDeleteDialogComponent, ModelDeletePopupComponent],
  entryComponents: [ModelDeleteDialogComponent]
})
export class GrammarEditorModelModule {}
