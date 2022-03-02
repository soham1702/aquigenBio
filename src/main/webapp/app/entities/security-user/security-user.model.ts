import dayjs from 'dayjs/esm';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { IPurchaseOrder } from 'app/entities/purchase-order/purchase-order.model';
import { IProduct } from 'app/entities/product/product.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { IProductQuatation } from 'app/entities/product-quatation/product-quatation.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { IConsumptionDetails } from 'app/entities/consumption-details/consumption-details.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';

export interface ISecurityUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  designation?: string | null;
  businessTitle?: string | null;
  gSTDetails?: string | null;
  login?: string;
  passwordHash?: string;
  email?: string | null;
  imageUrl?: string | null;
  activated?: boolean;
  langKey?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: dayjs.Dayjs | null;
  mobileNo?: string | null;
  oneTimePassword?: string | null;
  otpExpiryTime?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  productTransaction?: IProductTransaction | null;
  purchaseOrders?: IPurchaseOrder[] | null;
  products?: IProduct[] | null;
  securityPermissions?: ISecurityPermission[] | null;
  securityRoles?: ISecurityRole[] | null;
  productQuatation?: IProductQuatation | null;
  transfer?: ITransfer | null;
  consumptionDetails?: IConsumptionDetails | null;
  productInventories?: IProductInventory[] | null;
  warehouses?: IWarehouse[] | null;
}

export class SecurityUser implements ISecurityUser {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public designation?: string | null,
    public businessTitle?: string | null,
    public gSTDetails?: string | null,
    public login?: string,
    public passwordHash?: string,
    public email?: string | null,
    public imageUrl?: string | null,
    public activated?: boolean,
    public langKey?: string | null,
    public activationKey?: string | null,
    public resetKey?: string | null,
    public resetDate?: dayjs.Dayjs | null,
    public mobileNo?: string | null,
    public oneTimePassword?: string | null,
    public otpExpiryTime?: dayjs.Dayjs | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public productTransaction?: IProductTransaction | null,
    public purchaseOrders?: IPurchaseOrder[] | null,
    public products?: IProduct[] | null,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityRoles?: ISecurityRole[] | null,
    public productQuatation?: IProductQuatation | null,
    public transfer?: ITransfer | null,
    public consumptionDetails?: IConsumptionDetails | null,
    public productInventories?: IProductInventory[] | null,
    public warehouses?: IWarehouse[] | null
  ) {
    this.activated = this.activated ?? false;
  }
}

export function getSecurityUserIdentifier(securityUser: ISecurityUser): number | undefined {
  return securityUser.id;
}
