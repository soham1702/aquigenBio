import dayjs from 'dayjs/esm';
import { IPurchaseOrder } from 'app/entities/purchase-order/purchase-order.model';
import { IProduct } from 'app/entities/product/product.model';
import { IUnit } from 'app/entities/unit/unit.model';

export interface IPurchaseOrderDetails {
  id?: number;
  qtyordered?: number | null;
  gstTaxPercentage?: number | null;
  pricePerUnit?: number | null;
  totalPrice?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  purchaseOrder?: IPurchaseOrder | null;
  product?: IProduct | null;
  unit?: IUnit | null;
}

export class PurchaseOrderDetails implements IPurchaseOrderDetails {
  constructor(
    public id?: number,
    public qtyordered?: number | null,
    public gstTaxPercentage?: number | null,
    public pricePerUnit?: number | null,
    public totalPrice?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public purchaseOrder?: IPurchaseOrder | null,
    public product?: IProduct | null,
    public unit?: IUnit | null
  ) {}
}

export function getPurchaseOrderDetailsIdentifier(purchaseOrderDetails: IPurchaseOrderDetails): number | undefined {
  return purchaseOrderDetails.id;
}
