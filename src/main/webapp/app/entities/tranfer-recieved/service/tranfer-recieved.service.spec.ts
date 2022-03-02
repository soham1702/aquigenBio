import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';

import { TranferRecievedService } from './tranfer-recieved.service';

describe('TranferRecieved Service', () => {
  let service: TranferRecievedService;
  let httpMock: HttpTestingController;
  let elemDefault: ITranferRecieved;
  let expectedResult: ITranferRecieved | ITranferRecieved[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TranferRecievedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      transferDate: currentDate,
      qtyTransfered: 0,
      qtyReceived: 0,
      comment: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TranferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          transferDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new TranferRecieved()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TranferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyTransfered: 1,
          qtyReceived: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          transferDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TranferRecieved', () => {
      const patchObject = Object.assign(
        {
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyReceived: 1,
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          isActive: true,
        },
        new TranferRecieved()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          transferDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TranferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyTransfered: 1,
          qtyReceived: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          transferDate: currentDate,
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

    it('should delete a TranferRecieved', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTranferRecievedToCollectionIfMissing', () => {
      it('should add a TranferRecieved to an empty array', () => {
        const tranferRecieved: ITranferRecieved = { id: 123 };
        expectedResult = service.addTranferRecievedToCollectionIfMissing([], tranferRecieved);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tranferRecieved);
      });

      it('should not add a TranferRecieved to an array that contains it', () => {
        const tranferRecieved: ITranferRecieved = { id: 123 };
        const tranferRecievedCollection: ITranferRecieved[] = [
          {
            ...tranferRecieved,
          },
          { id: 456 },
        ];
        expectedResult = service.addTranferRecievedToCollectionIfMissing(tranferRecievedCollection, tranferRecieved);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TranferRecieved to an array that doesn't contain it", () => {
        const tranferRecieved: ITranferRecieved = { id: 123 };
        const tranferRecievedCollection: ITranferRecieved[] = [{ id: 456 }];
        expectedResult = service.addTranferRecievedToCollectionIfMissing(tranferRecievedCollection, tranferRecieved);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tranferRecieved);
      });

      it('should add only unique TranferRecieved to an array', () => {
        const tranferRecievedArray: ITranferRecieved[] = [{ id: 123 }, { id: 456 }, { id: 63764 }];
        const tranferRecievedCollection: ITranferRecieved[] = [{ id: 123 }];
        expectedResult = service.addTranferRecievedToCollectionIfMissing(tranferRecievedCollection, ...tranferRecievedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tranferRecieved: ITranferRecieved = { id: 123 };
        const tranferRecieved2: ITranferRecieved = { id: 456 };
        expectedResult = service.addTranferRecievedToCollectionIfMissing([], tranferRecieved, tranferRecieved2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tranferRecieved);
        expect(expectedResult).toContain(tranferRecieved2);
      });

      it('should accept null and undefined values', () => {
        const tranferRecieved: ITranferRecieved = { id: 123 };
        expectedResult = service.addTranferRecievedToCollectionIfMissing([], null, tranferRecieved, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tranferRecieved);
      });

      it('should return initial array if no TranferRecieved is added', () => {
        const tranferRecievedCollection: ITranferRecieved[] = [{ id: 123 }];
        expectedResult = service.addTranferRecievedToCollectionIfMissing(tranferRecievedCollection, undefined, null);
        expect(expectedResult).toEqual(tranferRecievedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
