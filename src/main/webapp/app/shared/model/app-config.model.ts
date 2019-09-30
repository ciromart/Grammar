import { IUser } from 'app/core/user/user.model';

export interface IAppConfig {
  id?: number;
  criticalWordsMaxFileSize?: number;
  criticalWordsMaxWords?: number;
  additionalContextMaxFileSize?: number;
  additionalContextMaxFileWords?: number;
  minOccurencyContext?: number;
  windowsMaxWords?: number;
  users?: IUser[];
}

export class AppConfig implements IAppConfig {
  constructor(
    public id?: number,
    public criticalWordsMaxFileSize?: number,
    public criticalWordsMaxWords?: number,
    public additionalContextMaxFileSize?: number,
    public additionalContextMaxFileWords?: number,
    public minOccurencyContext?: number,
    public windowsMaxWords?: number,
    public users?: IUser[]
  ) {}
}
