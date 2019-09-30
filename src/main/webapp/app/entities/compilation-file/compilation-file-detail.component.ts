import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompilationFile } from 'app/shared/model/compilation-file.model';

@Component({
  selector: 'jhi-compilation-file-detail',
  templateUrl: './compilation-file-detail.component.html'
})
export class CompilationFileDetailComponent implements OnInit {
  compilationFile: ICompilationFile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compilationFile }) => {
      this.compilationFile = compilationFile;
    });
  }

  previousState() {
    window.history.back();
  }
}
