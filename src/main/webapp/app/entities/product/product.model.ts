import dayjs from 'dayjs/esm';
import { IPurchaseOrderDetails } from 'app/entities/purchase-order-details/purchase-order-details.model';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { IQuatationDetails } from 'app/entities/quatation-details/quatation-details.model';
import { ICategories } from 'app/entities/categories/categories.model';
import { IUnit } from 'app/entities/unit/unit.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { ProductType } from 'app/entities/enumerations/product-type.model';

export interface IProduct {
  id?: number;
  shortName?: string | null;
  chemicalFormula?: string | null;
  hsnNo?: string | null;
  materialImage?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  productName?: string | null;
  alertUnits?: string | null;
  casNumber?: string | null;
  catlogNumber?: string | null;
  molecularWt?: number | null;
  molecularFormula?: string | null;
  chemicalName?: string | null;
  structureImg?: string | null;
  description?: string | null;
  qrCode?: string | null;
  barCode?: string | null;
  gstPercentage?: number | null;
  productType?: ProductType | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  purchaseOrderDetails?: IPurchaseOrderDetails[] | null;
  rawMaterialOrders?: IRawMaterialOrder[] | null;
  quatationDetails?: IQuatationDetails | null;
  categories?: ICategories | null;
  unit?: IUnit | null;
  securityUser?: ISecurityUser | null;
  productInventories?: IProductInventory[] | null;
  productTransactions?: IProductTransaction[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public shortName?: string | null,
    public chemicalFormula?: string | null,
    public hsnNo?: string | null,
    public materialImage?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public productName?: string | null,
    public alertUnits?: string | null,
    public casNumber?: string | null,
    public catlogNumber?: string | null,
    public molecularWt?: number | null,
    public molecularFormula?: string | null,
    public chemicalName?: string | null,
    public structureImg?: string | null,
    public description?: string | null,
    public qrCode?: string | null,
    public barCode?: string | null,
    public gstPercentage?: number | null,
    public productType?: ProductType | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public purchaseOrderDetails?: IPurchaseOrderDetails[] | null,
    public rawMaterialOrders?: IRawMaterialOrder[] | null,
    public quatationDetails?: IQuatationDetails | null,
    public categories?: ICategories | null,
    public unit?: IUnit | null,
    public securityUser?: ISecurityUser | null,
    public productInventories?: IProductInventory[] | null,
    public productTransactions?: IProductTransaction[] | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
