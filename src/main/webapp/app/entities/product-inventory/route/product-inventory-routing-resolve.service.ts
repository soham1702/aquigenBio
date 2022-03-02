import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductInventory, ProductInventory } from '../product-inventory.model';
import { ProductInventoryService } from '../service/product-inventory.service';

@Injectable({ providedIn: 'root' })
export class ProductInventoryRoutingResolveService implements Resolve<IProductInventory> {
  constructor(protected service: ProductInventoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductInventory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productInventory: HttpResponse<ProductInventory>) => {
          if (productInventory.body) {
            return of(productInventory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductInventory());
  }
}
