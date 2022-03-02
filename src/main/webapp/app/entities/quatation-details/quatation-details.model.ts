import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { IUnit } from 'app/entities/unit/unit.model';
import { ICategories } from 'app/entities/categories/categories.model';
import { IProductQuatation } from 'app/entities/product-quatation/product-quatation.model';

export interface IQuatationDetails {
  id?: number;
  availabelStock?: number | null;
  quantity?: number | null;
  ratsPerUnit?: number | null;
  totalprice?: number | null;
  discount?: number | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  product?: IProduct | null;
  unit?: IUnit | null;
  categories?: ICategories | null;
  productQuatation?: IProductQuatation | null;
}

export class QuatationDetails implements IQuatationDetails {
  constructor(
    public id?: number,
    public availabelStock?: number | null,
    public quantity?: number | null,
    public ratsPerUnit?: number | null,
    public totalprice?: number | null,
    public discount?: number | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public product?: IProduct | null,
    public unit?: IUnit | null,
    public categories?: ICategories | null,
    public productQuatation?: IProductQuatation | null
  ) {}
}

export function getQuatationDetailsIdentifier(quatationDetails: IQuatationDetails): number | undefined {
  return quatationDetails.id;
}
