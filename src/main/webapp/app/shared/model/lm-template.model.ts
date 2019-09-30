import { Moment } from 'moment';
import { IModel } from 'app/shared/model/model.model';

export interface ILmTemplate {
  id?: number;
  name?: string;
  langCode?: number;
  countryCode?: number;
  lmStandardCode?: number;
  path?: string;
  insertTs?: Moment;
  lastUpdateTs?: Moment;
  models?: IModel[];
}

export class LmTemplate implements ILmTemplate {
  constructor(
    public id?: number,
    public name?: string,
    public langCode?: number,
    public countryCode?: number,
    public lmStandardCode?: number,
    public path?: string,
    public insertTs?: Moment,
    public lastUpdateTs?: Moment,
    public models?: IModel[]
  ) {}
}
