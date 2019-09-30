import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrammarEditorSharedModule } from 'app/shared/shared.module';
import { AppConfigComponent } from './app-config.component';
import { AppConfigDetailComponent } from './app-config-detail.component';
import { AppConfigUpdateComponent } from './app-config-update.component';
import { AppConfigDeletePopupComponent, AppConfigDeleteDialogComponent } from './app-config-delete-dialog.component';
import { appConfigRoute, appConfigPopupRoute } from './app-config.route';

const ENTITY_STATES = [...appConfigRoute, ...appConfigPopupRoute];

@NgModule({
  imports: [GrammarEditorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppConfigComponent,
    AppConfigDetailComponent,
    AppConfigUpdateComponent,
    AppConfigDeleteDialogComponent,
    AppConfigDeletePopupComponent
  ],
  entryComponents: [AppConfigDeleteDialogComponent]
})
export class GrammarEditorAppConfigModule {}
