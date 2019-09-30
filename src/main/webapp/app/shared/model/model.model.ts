import { Moment } from 'moment';
import { ICompilationLog } from 'app/shared/model/compilation-log.model';
import { IUser } from 'app/core/user/user.model';
import { ILmTemplate } from 'app/shared/model/lm-template.model';

export interface IModel {
  id?: number;
  name?: string;
  mailNetworkName?: string;
  insertTs?: Moment;
  lastUpdateTs?: Moment;
  activated?: boolean;
  compilationLogs?: ICompilationLog[];
  users?: IUser[];
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
    public users?: IUser[],
    public lmTemplate?: ILmTemplate
  ) {
    this.activated = this.activated || false;
  }
}
