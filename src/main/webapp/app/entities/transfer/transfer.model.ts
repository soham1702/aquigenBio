import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';
import { ITranferDetailsApprovals } from 'app/entities/tranfer-details-approvals/tranfer-details-approvals.model';
import { ITranferRecieved } from 'app/entities/tranfer-recieved/tranfer-recieved.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITransfer {
  id?: number;
  tranferDate?: dayjs.Dayjs | null;
  comment?: string | null;
  isApproved?: boolean | null;
  isRecieved?: boolean | null;
  status?: Status | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUsers?: ISecurityUser[] | null;
  transferDetails?: ITransferDetails[] | null;
  tranferDetailsApprovals?: ITranferDetailsApprovals[] | null;
  tranferRecieveds?: ITranferRecieved[] | null;
  productInventory?: IProductInventory | null;
}

export class Transfer implements ITransfer {
  constructor(
    public id?: number,
    public tranferDate?: dayjs.Dayjs | null,
    public comment?: string | null,
    public isApproved?: boolean | null,
    public isRecieved?: boolean | null,
    public status?: Status | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUsers?: ISecurityUser[] | null,
    public transferDetails?: ITransferDetails[] | null,
    public tranferDetailsApprovals?: ITranferDetailsApprovals[] | null,
    public tranferRecieveds?: ITranferRecieved[] | null,
    public productInventory?: IProductInventory | null
  ) {
    this.isApproved = this.isApproved ?? false;
    this.isRecieved = this.isRecieved ?? false;
  }
}

export function getTransferIdentifier(transfer: ITransfer): number | undefined {
  return transfer.id;
}
