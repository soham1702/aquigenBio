import dayjs from 'dayjs/esm';
import { IPurchaseOrderDetails } from 'app/entities/purchase-order-details/purchase-order-details.model';
import { IProduct } from 'app/entities/product/product.model';
import { IQuatationDetails } from 'app/entities/quatation-details/quatation-details.model';

export interface IUnit {
  id?: number;
  unitName?: string | null;
  shortName?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  purchaseOrderDetails?: IPurchaseOrderDetails[] | null;
  products?: IProduct[] | null;
  quatationDetails?: IQuatationDetails | null;
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public unitName?: string | null,
    public shortName?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public purchaseOrderDetails?: IPurchaseOrderDetails[] | null,
    public products?: IProduct[] | null,
    public quatationDetails?: IQuatationDetails | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getUnitIdentifier(unit: IUnit): number | undefined {
  return unit.id;
}
