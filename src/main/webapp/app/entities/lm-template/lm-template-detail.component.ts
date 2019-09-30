import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILmTemplate } from 'app/shared/model/lm-template.model';

@Component({
  selector: 'jhi-lm-template-detail',
  templateUrl: './lm-template-detail.component.html'
})
export class LmTemplateDetailComponent implements OnInit {
  lmTemplate: ILmTemplate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lmTemplate }) => {
      this.lmTemplate = lmTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
