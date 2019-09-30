import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'lm-template',
        loadChildren: () => import('./lm-template/lm-template.module').then(m => m.GrammarEditorLmTemplateModule)
      },
      {
        path: 'model',
        loadChildren: () => import('./model/model.module').then(m => m.GrammarEditorModelModule)
      },
      {
        path: 'compilation-log',
        loadChildren: () => import('./compilation-log/compilation-log.module').then(m => m.GrammarEditorCompilationLogModule)
      },
      {
        path: 'compilation-file',
        loadChildren: () => import('./compilation-file/compilation-file.module').then(m => m.GrammarEditorCompilationFileModule)
      },
      {
        path: 'app-config',
        loadChildren: () => import('./app-config/app-config.module').then(m => m.GrammarEditorAppConfigModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GrammarEditorEntityModule {}
