import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppConfig } from 'app/shared/model/app-config.model';

@Component({
  selector: 'jhi-app-config-detail',
  templateUrl: './app-config-detail.component.html'
})
export class AppConfigDetailComponent implements OnInit {
  appConfig: IAppConfig;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appConfig }) => {
      this.appConfig = appConfig;
    });
  }

  previousState() {
    window.history.back();
  }
}
