import { ICompilationLog } from 'app/shared/model/compilation-log.model';

export interface ICompilationFile {
  id?: number;
  path?: string;
  compilationLog?: ICompilationLog;
}

export class CompilationFile implements ICompilationFile {
  constructor(public id?: number, public path?: string, public compilationLog?: ICompilationLog) {}
}
