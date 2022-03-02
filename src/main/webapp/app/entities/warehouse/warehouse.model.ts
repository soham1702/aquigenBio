import dayjs from 'dayjs/esm';
import { IPurchaseOrder } from 'app/entities/purchase-order/purchase-order.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';

export interface IWarehouse {
  id?: number;
  warehouseId?: number | null;
  whName?: string | null;
  address?: string | null;
  pincode?: number | null;
  city?: string | null;
  state?: string | null;
  country?: string | null;
  gSTDetails?: string | null;
  managerName?: string | null;
  managerEmail?: string | null;
  managerContact?: string | null;
  contact?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  purchaseOrders?: IPurchaseOrder[] | null;
  securityUsers?: ISecurityUser[] | null;
  productTransaction?: IProductTransaction | null;
  productInventories?: IProductInventory[] | null;
}

export class Warehouse implements IWarehouse {
  constructor(
    public id?: number,
    public warehouseId?: number | null,
    public whName?: string | null,
    public address?: string | null,
    public pincode?: number | null,
    public city?: string | null,
    public state?: string | null,
    public country?: string | null,
    public gSTDetails?: string | null,
    public managerName?: string | null,
    public managerEmail?: string | null,
    public managerContact?: string | null,
    public contact?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public purchaseOrders?: IPurchaseOrder[] | null,
    public securityUsers?: ISecurityUser[] | null,
    public productTransaction?: IProductTransaction | null,
    public productInventories?: IProductInventory[] | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getWarehouseIdentifier(warehouse: IWarehouse): number | undefined {
  return warehouse.id;
}
