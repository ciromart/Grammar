import { Moment } from 'moment';
import { ICompilationFile } from 'app/shared/model/compilation-file.model';
import { IModel } from 'app/shared/model/model.model';

export interface ICompilationLog {
  id?: number;
  insertTs?: Moment;
  lastUpdatTs?: Moment;
  status?: string;
  rpkLink?: string;
  compilationFiles?: ICompilationFile[];
  model?: IModel;
}

export class CompilationLog implements ICompilationLog {
  constructor(
    public id?: number,
    public insertTs?: Moment,
    public lastUpdatTs?: Moment,
    public status?: string,
    public rpkLink?: string,
    public compilationFiles?: ICompilationFile[],
    public model?: IModel
  ) {}
}
