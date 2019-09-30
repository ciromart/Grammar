import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompilationLog } from 'app/shared/model/compilation-log.model';

@Component({
  selector: 'jhi-compilation-log-detail',
  templateUrl: './compilation-log-detail.component.html'
})
export class CompilationLogDetailComponent implements OnInit {
  compilationLog: ICompilationLog;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compilationLog }) => {
      this.compilationLog = compilationLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
