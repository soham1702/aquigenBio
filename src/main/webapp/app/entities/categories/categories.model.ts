import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { IQuatationDetails } from 'app/entities/quatation-details/quatation-details.model';

export interface ICategories {
  id?: number;
  categoryName?: string | null;
  categoryDescription?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  product?: IProduct | null;
  quatationDetails?: IQuatationDetails | null;
}

export class Categories implements ICategories {
  constructor(
    public id?: number,
    public categoryName?: string | null,
    public categoryDescription?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public product?: IProduct | null,
    public quatationDetails?: IQuatationDetails | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getCategoriesIdentifier(categories: ICategories): number | undefined {
  return categories.id;
}
