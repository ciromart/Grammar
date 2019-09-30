import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrammarEditorSharedModule } from 'app/shared/shared.module';
import { CompilationFileComponent } from './compilation-file.component';
import { CompilationFileDetailComponent } from './compilation-file-detail.component';
import { CompilationFileUpdateComponent } from './compilation-file-update.component';
import { CompilationFileDeletePopupComponent, CompilationFileDeleteDialogComponent } from './compilation-file-delete-dialog.component';
import { compilationFileRoute, compilationFilePopupRoute } from './compilation-file.route';

const ENTITY_STATES = [...compilationFileRoute, ...compilationFilePopupRoute];

@NgModule({
  imports: [GrammarEditorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompilationFileComponent,
    CompilationFileDetailComponent,
    CompilationFileUpdateComponent,
    CompilationFileDeleteDialogComponent,
    CompilationFileDeletePopupComponent
  ],
  entryComponents: [CompilationFileDeleteDialogComponent]
})
export class GrammarEditorCompilationFileModule {}
