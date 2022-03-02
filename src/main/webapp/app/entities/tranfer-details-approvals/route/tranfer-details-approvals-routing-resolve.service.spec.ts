import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITranferDetailsApprovals, TranferDetailsApprovals } from '../tranfer-details-approvals.model';
import { TranferDetailsApprovalsService } from '../service/tranfer-details-approvals.service';

import { TranferDetailsApprovalsRoutingResolveService } from './tranfer-details-approvals-routing-resolve.service';

describe('TranferDetailsApprovals routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TranferDetailsApprovalsRoutingResolveService;
  let service: TranferDetailsApprovalsService;
  let resultTranferDetailsApprovals: ITranferDetailsApprovals | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TranferDetailsApprovalsRoutingResolveService);
    service = TestBed.inject(TranferDetailsApprovalsService);
    resultTranferDetailsApprovals = undefined;
  });

  describe('resolve', () => {
    it('should return ITranferDetailsApprovals returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTranferDetailsApprovals).toEqual({ id: 123 });
    });

    it('should return new ITranferDetailsApprovals if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTranferDetailsApprovals).toEqual(new TranferDetailsApprovals());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TranferDetailsApprovals })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTranferDetailsApprovals).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
