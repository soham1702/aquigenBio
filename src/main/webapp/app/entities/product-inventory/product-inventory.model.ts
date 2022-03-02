import dayjs from 'dayjs/esm';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { IPurchaseOrder } from 'app/entities/purchase-order/purchase-order.model';
import { IConsumptionDetails } from 'app/entities/consumption-details/consumption-details.model';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { IProduct } from 'app/entities/product/product.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface IProductInventory {
  id?: number;
  inwardOutwardDate?: dayjs.Dayjs | null;
  inwardQty?: string | null;
  outwardQty?: string | null;
  totalQuanity?: string | null;
  pricePerUnit?: number | null;
  lotNo?: string | null;
  expiryDate?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  transfers?: ITransfer[] | null;
  purchaseOrders?: IPurchaseOrder[] | null;
  consumptionDetails?: IConsumptionDetails[] | null;
  productTransactions?: IProductTransaction[] | null;
  products?: IProduct[] | null;
  warehouses?: IWarehouse[] | null;
  securityUsers?: ISecurityUser[] | null;
}

export class ProductInventory implements IProductInventory {
  constructor(
    public id?: number,
    public inwardOutwardDate?: dayjs.Dayjs | null,
    public inwardQty?: string | null,
    public outwardQty?: string | null,
    public totalQuanity?: string | null,
    public pricePerUnit?: number | null,
    public lotNo?: string | null,
    public expiryDate?: dayjs.Dayjs | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public transfers?: ITransfer[] | null,
    public purchaseOrders?: IPurchaseOrder[] | null,
    public consumptionDetails?: IConsumptionDetails[] | null,
    public productTransactions?: IProductTransaction[] | null,
    public products?: IProduct[] | null,
    public warehouses?: IWarehouse[] | null,
    public securityUsers?: ISecurityUser[] | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getProductInventoryIdentifier(productInventory: IProductInventory): number | undefined {
  return productInventory.id;
}
