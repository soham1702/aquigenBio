import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRawMaterialOrder, RawMaterialOrder } from '../raw-material-order.model';
import { RawMaterialOrderService } from '../service/raw-material-order.service';

import { RawMaterialOrderRoutingResolveService } from './raw-material-order-routing-resolve.service';

describe('RawMaterialOrder routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RawMaterialOrderRoutingResolveService;
  let service: RawMaterialOrderService;
  let resultRawMaterialOrder: IRawMaterialOrder | undefined;

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
    routingResolveService = TestBed.inject(RawMaterialOrderRoutingResolveService);
    service = TestBed.inject(RawMaterialOrderService);
    resultRawMaterialOrder = undefined;
  });

  describe('resolve', () => {
    it('should return IRawMaterialOrder returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialOrder = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRawMaterialOrder).toEqual({ id: 123 });
    });

    it('should return new IRawMaterialOrder if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialOrder = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRawMaterialOrder).toEqual(new RawMaterialOrder());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RawMaterialOrder })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialOrder = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRawMaterialOrder).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
