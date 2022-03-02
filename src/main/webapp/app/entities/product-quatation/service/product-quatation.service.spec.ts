import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductQuatation, ProductQuatation } from '../product-quatation.model';

import { ProductQuatationService } from './product-quatation.service';

describe('ProductQuatation Service', () => {
  let service: ProductQuatationService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductQuatation;
  let expectedResult: IProductQuatation | IProductQuatation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductQuatationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      quatationdate: currentDate,
      totalAmt: 0,
      gst: 0,
      discount: 0,
      expectedDelivery: currentDate,
      deliveryAddress: 'AAAAAAA',
      quoValidity: currentDate,
      clientName: 'AAAAAAA',
      clientMobile: 'AAAAAAA',
      clientEmail: 'AAAAAAA',
      termsAndCondition: 'AAAAAAA',
      notes: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      freeField3: 'AAAAAAA',
      freeField4: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          quatationdate: currentDate.format(DATE_TIME_FORMAT),
          expectedDelivery: currentDate.format(DATE_TIME_FORMAT),
          quoValidity: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProductQuatation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          quatationdate: currentDate.format(DATE_TIME_FORMAT),
          expectedDelivery: currentDate.format(DATE_TIME_FORMAT),
          quoValidity: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          quatationdate: currentDate,
          expectedDelivery: currentDate,
          quoValidity: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new ProductQuatation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductQuatation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quatationdate: currentDate.format(DATE_TIME_FORMAT),
          totalAmt: 1,
          gst: 1,
          discount: 1,
          expectedDelivery: currentDate.format(DATE_TIME_FORMAT),
          deliveryAddress: 'BBBBBB',
          quoValidity: currentDate.format(DATE_TIME_FORMAT),
          clientName: 'BBBBBB',
          clientMobile: 'BBBBBB',
          clientEmail: 'BBBBBB',
          termsAndCondition: 'BBBBBB',
          notes: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          quatationdate: currentDate,
          expectedDelivery: currentDate,
          quoValidity: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductQuatation', () => {
      const patchObject = Object.assign(
        {
          gst: 1,
          expectedDelivery: currentDate.format(DATE_TIME_FORMAT),
          deliveryAddress: 'BBBBBB',
          quoValidity: currentDate.format(DATE_TIME_FORMAT),
          clientEmail: 'BBBBBB',
          termsAndCondition: 'BBBBBB',
          notes: 'BBBBBB',
          freeField3: 'BBBBBB',
        },
        new ProductQuatation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          quatationdate: currentDate,
          expectedDelivery: currentDate,
          quoValidity: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductQuatation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quatationdate: currentDate.format(DATE_TIME_FORMAT),
          totalAmt: 1,
          gst: 1,
          discount: 1,
          expectedDelivery: currentDate.format(DATE_TIME_FORMAT),
          deliveryAddress: 'BBBBBB',
          quoValidity: currentDate.format(DATE_TIME_FORMAT),
          clientName: 'BBBBBB',
          clientMobile: 'BBBBBB',
          clientEmail: 'BBBBBB',
          termsAndCondition: 'BBBBBB',
          notes: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          quatationdate: currentDate,
          expectedDelivery: currentDate,
          quoValidity: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProductQuatation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductQuatationToCollectionIfMissing', () => {
      it('should add a ProductQuatation to an empty array', () => {
        const productQuatation: IProductQuatation = { id: 123 };
        expectedResult = service.addProductQuatationToCollectionIfMissing([], productQuatation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productQuatation);
      });

      it('should not add a ProductQuatation to an array that contains it', () => {
        const productQuatation: IProductQuatation = { id: 123 };
        const productQuatationCollection: IProductQuatation[] = [
          {
            ...productQuatation,
          },
          { id: 456 },
        ];
        expectedResult = service.addProductQuatationToCollectionIfMissing(productQuatationCollection, productQuatation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductQuatation to an array that doesn't contain it", () => {
        const productQuatation: IProductQuatation = { id: 123 };
        const productQuatationCollection: IProductQuatation[] = [{ id: 456 }];
        expectedResult = service.addProductQuatationToCollectionIfMissing(productQuatationCollection, productQuatation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productQuatation);
      });

      it('should add only unique ProductQuatation to an array', () => {
        const productQuatationArray: IProductQuatation[] = [{ id: 123 }, { id: 456 }, { id: 96928 }];
        const productQuatationCollection: IProductQuatation[] = [{ id: 123 }];
        expectedResult = service.addProductQuatationToCollectionIfMissing(productQuatationCollection, ...productQuatationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productQuatation: IProductQuatation = { id: 123 };
        const productQuatation2: IProductQuatation = { id: 456 };
        expectedResult = service.addProductQuatationToCollectionIfMissing([], productQuatation, productQuatation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productQuatation);
        expect(expectedResult).toContain(productQuatation2);
      });

      it('should accept null and undefined values', () => {
        const productQuatation: IProductQuatation = { id: 123 };
        expectedResult = service.addProductQuatationToCollectionIfMissing([], null, productQuatation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productQuatation);
      });

      it('should return initial array if no ProductQuatation is added', () => {
        const productQuatationCollection: IProductQuatation[] = [{ id: 123 }];
        expectedResult = service.addProductQuatationToCollectionIfMissing(productQuatationCollection, undefined, null);
        expect(expectedResult).toEqual(productQuatationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
