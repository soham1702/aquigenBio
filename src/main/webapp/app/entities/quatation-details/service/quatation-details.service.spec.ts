import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IQuatationDetails, QuatationDetails } from '../quatation-details.model';

import { QuatationDetailsService } from './quatation-details.service';

describe('QuatationDetails Service', () => {
  let service: QuatationDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IQuatationDetails;
  let expectedResult: IQuatationDetails | IQuatationDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QuatationDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      availabelStock: 0,
      quantity: 0,
      ratsPerUnit: 0,
      totalprice: 0,
      discount: 0,
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
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a QuatationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new QuatationDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QuatationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          availabelStock: 1,
          quantity: 1,
          ratsPerUnit: 1,
          totalprice: 1,
          discount: 1,
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
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QuatationDetails', () => {
      const patchObject = Object.assign(
        {
          availabelStock: 1,
          discount: 1,
          freeField1: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new QuatationDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QuatationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          availabelStock: 1,
          quantity: 1,
          ratsPerUnit: 1,
          totalprice: 1,
          discount: 1,
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

    it('should delete a QuatationDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addQuatationDetailsToCollectionIfMissing', () => {
      it('should add a QuatationDetails to an empty array', () => {
        const quatationDetails: IQuatationDetails = { id: 123 };
        expectedResult = service.addQuatationDetailsToCollectionIfMissing([], quatationDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(quatationDetails);
      });

      it('should not add a QuatationDetails to an array that contains it', () => {
        const quatationDetails: IQuatationDetails = { id: 123 };
        const quatationDetailsCollection: IQuatationDetails[] = [
          {
            ...quatationDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addQuatationDetailsToCollectionIfMissing(quatationDetailsCollection, quatationDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QuatationDetails to an array that doesn't contain it", () => {
        const quatationDetails: IQuatationDetails = { id: 123 };
        const quatationDetailsCollection: IQuatationDetails[] = [{ id: 456 }];
        expectedResult = service.addQuatationDetailsToCollectionIfMissing(quatationDetailsCollection, quatationDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(quatationDetails);
      });

      it('should add only unique QuatationDetails to an array', () => {
        const quatationDetailsArray: IQuatationDetails[] = [{ id: 123 }, { id: 456 }, { id: 71384 }];
        const quatationDetailsCollection: IQuatationDetails[] = [{ id: 123 }];
        expectedResult = service.addQuatationDetailsToCollectionIfMissing(quatationDetailsCollection, ...quatationDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const quatationDetails: IQuatationDetails = { id: 123 };
        const quatationDetails2: IQuatationDetails = { id: 456 };
        expectedResult = service.addQuatationDetailsToCollectionIfMissing([], quatationDetails, quatationDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(quatationDetails);
        expect(expectedResult).toContain(quatationDetails2);
      });

      it('should accept null and undefined values', () => {
        const quatationDetails: IQuatationDetails = { id: 123 };
        expectedResult = service.addQuatationDetailsToCollectionIfMissing([], null, quatationDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(quatationDetails);
      });

      it('should return initial array if no QuatationDetails is added', () => {
        const quatationDetailsCollection: IQuatationDetails[] = [{ id: 123 }];
        expectedResult = service.addQuatationDetailsToCollectionIfMissing(quatationDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(quatationDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
