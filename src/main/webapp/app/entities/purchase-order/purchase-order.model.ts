import dayjs from 'dayjs/esm';
import { IPurchaseOrderDetails } from 'app/entities/purchase-order-details/purchase-order-details.model';
import { IGoodsRecived } from 'app/entities/goods-recived/goods-recived.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IPurchaseOrder {
  id?: number;
  totalPOAmount?: number | null;
  totalGSTAmount?: number | null;
  expectedDeliveryDate?: dayjs.Dayjs | null;
  poDate?: dayjs.Dayjs | null;
  orderStatus?: Status | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  purchaseOrderDetails?: IPurchaseOrderDetails[] | null;
  goodsReciveds?: IGoodsRecived[] | null;
  productInventory?: IProductInventory | null;
  warehouse?: IWarehouse | null;
  securityUser?: ISecurityUser | null;
  rawMaterialOrder?: IRawMaterialOrder | null;
}

export class PurchaseOrder implements IPurchaseOrder {
  constructor(
    public id?: number,
    public totalPOAmount?: number | null,
    public totalGSTAmount?: number | null,
    public expectedDeliveryDate?: dayjs.Dayjs | null,
    public poDate?: dayjs.Dayjs | null,
    public orderStatus?: Status | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public purchaseOrderDetails?: IPurchaseOrderDetails[] | null,
    public goodsReciveds?: IGoodsRecived[] | null,
    public productInventory?: IProductInventory | null,
    public warehouse?: IWarehouse | null,
    public securityUser?: ISecurityUser | null,
    public rawMaterialOrder?: IRawMaterialOrder | null
  ) {}
}

export function getPurchaseOrderIdentifier(purchaseOrder: IPurchaseOrder): number | undefined {
  return purchaseOrder.id;
}
