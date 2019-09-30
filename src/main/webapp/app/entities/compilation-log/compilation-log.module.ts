import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrammarEditorSharedModule } from 'app/shared/shared.module';
import { CompilationLogComponent } from './compilation-log.component';
import { CompilationLogDetailComponent } from './compilation-log-detail.component';
import { CompilationLogUpdateComponent } from './compilation-log-update.component';
import { CompilationLogDeletePopupComponent, CompilationLogDeleteDialogComponent } from './compilation-log-delete-dialog.component';
import { compilationLogRoute, compilationLogPopupRoute } from './compilation-log.route';

const ENTITY_STATES = [...compilationLogRoute, ...compilationLogPopupRoute];

@NgModule({
  imports: [GrammarEditorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompilationLogComponent,
    CompilationLogDetailComponent,
    CompilationLogUpdateComponent,
    CompilationLogDeleteDialogComponent,
    CompilationLogDeletePopupComponent
  ],
  entryComponents: [CompilationLogDeleteDialogComponent]
})
export class GrammarEditorCompilationLogModule {}
