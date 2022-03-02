import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';
import { TranferRecievedService } from '../service/tranfer-recieved.service';

import { TranferRecievedRoutingResolveService } from './tranfer-recieved-routing-resolve.service';

describe('TranferRecieved routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TranferRecievedRoutingResolveService;
  let service: TranferRecievedService;
  let resultTranferRecieved: ITranferRecieved | undefined;

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
    routingResolveService = TestBed.inject(TranferRecievedRoutingResolveService);
    service = TestBed.inject(TranferRecievedService);
    resultTranferRecieved = undefined;
  });

  describe('resolve', () => {
    it('should return ITranferRecieved returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferRecieved = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTranferRecieved).toEqual({ id: 123 });
    });

    it('should return new ITranferRecieved if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferRecieved = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTranferRecieved).toEqual(new TranferRecieved());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TranferRecieved })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTranferRecieved = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTranferRecieved).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
