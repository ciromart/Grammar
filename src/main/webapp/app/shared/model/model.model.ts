import { Moment } from 'moment';
import { ICompilationLog } from 'app/shared/model/compilation-log.model';
import { ILmTemplate } from 'app/shared/model/lm-template.model';

export interface IModel {
  id?: number;
  name?: string;
  mailNetworkName?: string;
  insertTs?: Moment;
  lastUpdateTs?: Moment;
  activated?: boolean;
  compilationLogs?: ICompilationLog[];
  lmTemplate?: ILmTemplate;
}

export class Model implements IModel {
  constructor(
    public id?: number,
    public name?: string,
    public mailNetworkName?: string,
    public insertTs?: Moment,
    public lastUpdateTs?: Moment,
    public activated?: boolean,
    public compilationLogs?: ICompilationLog[],
    public lmTemplate?: ILmTemplate
  ) {
    this.activated = this.activated || false;
  }
}