import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { AccessLevel } from 'app/entities/enumerations/access-level.model';

export interface IUserAccess {
  id?: number;
  level?: AccessLevel | null;
  accessId?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  securityUser?: ISecurityUser | null;
}

export class UserAccess implements IUserAccess {
  constructor(
    public id?: number,
    public level?: AccessLevel | null,
    public accessId?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public securityUser?: ISecurityUser | null
  ) {}
}

export function getUserAccessIdentifier(userAccess: IUserAccess): number | undefined {
  return userAccess.id;
}
