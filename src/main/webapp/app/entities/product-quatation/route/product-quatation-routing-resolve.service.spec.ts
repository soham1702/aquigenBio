import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProductQuatation, ProductQuatation } from '../product-quatation.model';
import { ProductQuatationService } from '../service/product-quatation.service';

import { ProductQuatationRoutingResolveService } from './product-quatation-routing-resolve.service';

describe('ProductQuatation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductQuatationRoutingResolveService;
  let service: ProductQuatationService;
  let resultProductQuatation: IProductQuatation | undefined;

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
    routingResolveService = TestBed.inject(ProductQuatationRoutingResolveService);
    service = TestBed.inject(ProductQuatationService);
    resultProductQuatation = undefined;
  });

  describe('resolve', () => {
    it('should return IProductQuatation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductQuatation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProductQuatation).toEqual({ id: 123 });
    });

    it('should return new IProductQuatation if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductQuatation = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductQuatation).toEqual(new ProductQuatation());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProductQuatation })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductQuatation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProductQuatation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
