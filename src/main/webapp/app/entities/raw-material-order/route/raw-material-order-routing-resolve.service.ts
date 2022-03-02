import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRawMaterialOrder, RawMaterialOrder } from '../raw-material-order.model';
import { RawMaterialOrderService } from '../service/raw-material-order.service';

@Injectable({ providedIn: 'root' })
export class RawMaterialOrderRoutingResolveService implements Resolve<IRawMaterialOrder> {
  constructor(protected service: RawMaterialOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRawMaterialOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rawMaterialOrder: HttpResponse<RawMaterialOrder>) => {
          if (rawMaterialOrder.body) {
            return of(rawMaterialOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RawMaterialOrder());
  }
}
