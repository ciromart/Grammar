export interface IAppConfig {
  id?: number;
  criticalWordsMaxFileSize?: number;
  criticalWordsMaxWords?: number;
  additionalContextMaxFileSize?: number;
  additionalContextMaxFileWords?: number;
  minOccurencyContext?: number;
  windowsMaxWords?: number;
}

export class AppConfig implements IAppConfig {
  constructor(
    public id?: number,
    public criticalWordsMaxFileSize?: number,
    public criticalWordsMaxWords?: number,
    public additionalContextMaxFileSize?: number,
    public additionalContextMaxFileWords?: number,
    public minOccurencyContext?: number,
    public windowsMaxWords?: number
  ) {}
}
