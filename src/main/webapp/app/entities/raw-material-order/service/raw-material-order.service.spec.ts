import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRawMaterialOrder, RawMaterialOrder } from '../raw-material-order.model';

import { RawMaterialOrderService } from './raw-material-order.service';

describe('RawMaterialOrder Service', () => {
  let service: RawMaterialOrderService;
  let httpMock: HttpTestingController;
  let elemDefault: IRawMaterialOrder;
  let expectedResult: IRawMaterialOrder | IRawMaterialOrder[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RawMaterialOrderService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      pricePerUnit: 0,
      quantityUnit: 'AAAAAAA',
      quantity: 0,
      deliveryDate: currentDate,
      quantityCheck: 'AAAAAAA',
      orderedOn: currentDate,
      orderStatus: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      freeField3: 'AAAAAAA',
      freeField4: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          deliveryDate: currentDate.format(DATE_TIME_FORMAT),
          orderedOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RawMaterialOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          deliveryDate: currentDate.format(DATE_TIME_FORMAT),
          orderedOn: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          deliveryDate: currentDate,
          orderedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new RawMaterialOrder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RawMaterialOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pricePerUnit: 1,
          quantityUnit: 'BBBBBB',
          quantity: 1,
          deliveryDate: currentDate.format(DATE_TIME_FORMAT),
          quantityCheck: 'BBBBBB',
          orderedOn: currentDate.format(DATE_TIME_FORMAT),
          orderStatus: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          deliveryDate: currentDate,
          orderedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RawMaterialOrder', () => {
      const patchObject = Object.assign(
        {
          deliveryDate: currentDate.format(DATE_TIME_FORMAT),
          quantityCheck: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        new RawMaterialOrder()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          deliveryDate: currentDate,
          orderedOn: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RawMaterialOrder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pricePerUnit: 1,
          quantityUnit: 'BBBBBB',
          quantity: 1,
          deliveryDate: currentDate.format(DATE_TIME_FORMAT),
          quantityCheck: 'BBBBBB',
          orderedOn: currentDate.format(DATE_TIME_FORMAT),
          orderStatus: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          deliveryDate: currentDate,
          orderedOn: currentDate,
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

    it('should delete a RawMaterialOrder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRawMaterialOrderToCollectionIfMissing', () => {
      it('should add a RawMaterialOrder to an empty array', () => {
        const rawMaterialOrder: IRawMaterialOrder = { id: 123 };
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing([], rawMaterialOrder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterialOrder);
      });

      it('should not add a RawMaterialOrder to an array that contains it', () => {
        const rawMaterialOrder: IRawMaterialOrder = { id: 123 };
        const rawMaterialOrderCollection: IRawMaterialOrder[] = [
          {
            ...rawMaterialOrder,
          },
          { id: 456 },
        ];
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing(rawMaterialOrderCollection, rawMaterialOrder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RawMaterialOrder to an array that doesn't contain it", () => {
        const rawMaterialOrder: IRawMaterialOrder = { id: 123 };
        const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 456 }];
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing(rawMaterialOrderCollection, rawMaterialOrder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterialOrder);
      });

      it('should add only unique RawMaterialOrder to an array', () => {
        const rawMaterialOrderArray: IRawMaterialOrder[] = [{ id: 123 }, { id: 456 }, { id: 87859 }];
        const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing(rawMaterialOrderCollection, ...rawMaterialOrderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rawMaterialOrder: IRawMaterialOrder = { id: 123 };
        const rawMaterialOrder2: IRawMaterialOrder = { id: 456 };
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing([], rawMaterialOrder, rawMaterialOrder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterialOrder);
        expect(expectedResult).toContain(rawMaterialOrder2);
      });

      it('should accept null and undefined values', () => {
        const rawMaterialOrder: IRawMaterialOrder = { id: 123 };
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing([], null, rawMaterialOrder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterialOrder);
      });

      it('should return initial array if no RawMaterialOrder is added', () => {
        const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialOrderToCollectionIfMissing(rawMaterialOrderCollection, undefined, null);
        expect(expectedResult).toEqual(rawMaterialOrderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
