import dayjs from 'dayjs/esm';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { IProduct } from 'app/entities/product/product.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';

export interface IProductTransaction {
  id?: number;
  qtySold?: number | null;
  pricePerUnit?: number | null;
  lotNumber?: string | null;
  expirydate?: dayjs.Dayjs | null;
  totalAmount?: number | null;
  gstAmount?: number | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  warehouse?: IWarehouse | null;
  products?: IProduct[] | null;
  securityUser?: ISecurityUser | null;
  productInventory?: IProductInventory | null;
}

export class ProductTransaction implements IProductTransaction {
  constructor(
    public id?: number,
    public qtySold?: number | null,
    public pricePerUnit?: number | null,
    public lotNumber?: string | null,
    public expirydate?: dayjs.Dayjs | null,
    public totalAmount?: number | null,
    public gstAmount?: number | null,
    public description?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public warehouse?: IWarehouse | null,
    public products?: IProduct[] | null,
    public securityUser?: ISecurityUser | null,
    public productInventory?: IProductInventory | null
  ) {}
}

export function getProductTransactionIdentifier(productTransaction: IProductTransaction): number | undefined {
  return productTransaction.id;
}
