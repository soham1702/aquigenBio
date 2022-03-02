import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IQuatationDetails } from 'app/entities/quatation-details/quatation-details.model';

export interface IProductQuatation {
  id?: number;
  quatationdate?: dayjs.Dayjs | null;
  totalAmt?: number | null;
  gst?: number | null;
  discount?: number | null;
  expectedDelivery?: dayjs.Dayjs | null;
  deliveryAddress?: string | null;
  quoValidity?: dayjs.Dayjs | null;
  clientName?: string | null;
  clientMobile?: string | null;
  clientEmail?: string | null;
  termsAndCondition?: string | null;
  notes?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
  quatationDetails?: IQuatationDetails | null;
}

export class ProductQuatation implements IProductQuatation {
  constructor(
    public id?: number,
    public quatationdate?: dayjs.Dayjs | null,
    public totalAmt?: number | null,
    public gst?: number | null,
    public discount?: number | null,
    public expectedDelivery?: dayjs.Dayjs | null,
    public deliveryAddress?: string | null,
    public quoValidity?: dayjs.Dayjs | null,
    public clientName?: string | null,
    public clientMobile?: string | null,
    public clientEmail?: string | null,
    public termsAndCondition?: string | null,
    public notes?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null,
    public quatationDetails?: IQuatationDetails | null
  ) {}
}

export function getProductQuatationIdentifier(productQuatation: IProductQuatation): number | undefined {
  return productQuatation.id;
}
