import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductQuatation, ProductQuatation } from '../product-quatation.model';
import { ProductQuatationService } from '../service/product-quatation.service';

@Injectable({ providedIn: 'root' })
export class ProductQuatationRoutingResolveService implements Resolve<IProductQuatation> {
  constructor(protected service: ProductQuatationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductQuatation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productQuatation: HttpResponse<ProductQuatation>) => {
          if (productQuatation.body) {
            return of(productQuatation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductQuatation());
  }
}
