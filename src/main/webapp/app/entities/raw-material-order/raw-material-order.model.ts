import dayjs from 'dayjs/esm';
import { IPurchaseOrder } from 'app/entities/purchase-order/purchase-order.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IRawMaterialOrder {
  id?: number;
  pricePerUnit?: number | null;
  quantityUnit?: string | null;
  quantity?: number | null;
  deliveryDate?: dayjs.Dayjs | null;
  quantityCheck?: string | null;
  orderedOn?: dayjs.Dayjs | null;
  orderStatus?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  purchaseOrders?: IPurchaseOrder[] | null;
  products?: IProduct[] | null;
}

export class RawMaterialOrder implements IRawMaterialOrder {
  constructor(
    public id?: number,
    public pricePerUnit?: number | null,
    public quantityUnit?: string | null,
    public quantity?: number | null,
    public deliveryDate?: dayjs.Dayjs | null,
    public quantityCheck?: string | null,
    public orderedOn?: dayjs.Dayjs | null,
    public orderStatus?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public purchaseOrders?: IPurchaseOrder[] | null,
    public products?: IProduct[] | null
  ) {}
}

export function getRawMaterialOrderIdentifier(rawMaterialOrder: IRawMaterialOrder): number | undefined {
  return rawMaterialOrder.id;
}
