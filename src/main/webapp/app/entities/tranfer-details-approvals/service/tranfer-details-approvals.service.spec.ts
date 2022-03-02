import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITranferDetailsApprovals, TranferDetailsApprovals } from '../tranfer-details-approvals.model';

import { TranferDetailsApprovalsService } from './tranfer-details-approvals.service';

describe('TranferDetailsApprovals Service', () => {
  let service: TranferDetailsApprovalsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITranferDetailsApprovals;
  let expectedResult: ITranferDetailsApprovals | ITranferDetailsApprovals[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TranferDetailsApprovalsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      approvalDate: currentDate,
      qtyRequested: 0,
      qtyApproved: 0,
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
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TranferDetailsApprovals', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          approvalDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new TranferDetailsApprovals()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TranferDetailsApprovals', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qtyRequested: 1,
          qtyApproved: 1,
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
          approvalDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TranferDetailsApprovals', () => {
      const patchObject = Object.assign(
        {
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isActive: true,
        },
        new TranferDetailsApprovals()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          approvalDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TranferDetailsApprovals', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qtyRequested: 1,
          qtyApproved: 1,
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
          approvalDate: currentDate,
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

    it('should delete a TranferDetailsApprovals', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTranferDetailsApprovalsToCollectionIfMissing', () => {
      it('should add a TranferDetailsApprovals to an empty array', () => {
        const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 123 };
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing([], tranferDetailsApprovals);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tranferDetailsApprovals);
      });

      it('should not add a TranferDetailsApprovals to an array that contains it', () => {
        const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 123 };
        const tranferDetailsApprovalsCollection: ITranferDetailsApprovals[] = [
          {
            ...tranferDetailsApprovals,
          },
          { id: 456 },
        ];
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing(
          tranferDetailsApprovalsCollection,
          tranferDetailsApprovals
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TranferDetailsApprovals to an array that doesn't contain it", () => {
        const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 123 };
        const tranferDetailsApprovalsCollection: ITranferDetailsApprovals[] = [{ id: 456 }];
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing(
          tranferDetailsApprovalsCollection,
          tranferDetailsApprovals
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tranferDetailsApprovals);
      });

      it('should add only unique TranferDetailsApprovals to an array', () => {
        const tranferDetailsApprovalsArray: ITranferDetailsApprovals[] = [{ id: 123 }, { id: 456 }, { id: 58350 }];
        const tranferDetailsApprovalsCollection: ITranferDetailsApprovals[] = [{ id: 123 }];
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing(
          tranferDetailsApprovalsCollection,
          ...tranferDetailsApprovalsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 123 };
        const tranferDetailsApprovals2: ITranferDetailsApprovals = { id: 456 };
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing([], tranferDetailsApprovals, tranferDetailsApprovals2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tranferDetailsApprovals);
        expect(expectedResult).toContain(tranferDetailsApprovals2);
      });

      it('should accept null and undefined values', () => {
        const tranferDetailsApprovals: ITranferDetailsApprovals = { id: 123 };
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing([], null, tranferDetailsApprovals, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tranferDetailsApprovals);
      });

      it('should return initial array if no TranferDetailsApprovals is added', () => {
        const tranferDetailsApprovalsCollection: ITranferDetailsApprovals[] = [{ id: 123 }];
        expectedResult = service.addTranferDetailsApprovalsToCollectionIfMissing(tranferDetailsApprovalsCollection, undefined, null);
        expect(expectedResult).toEqual(tranferDetailsApprovalsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
