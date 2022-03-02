import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';
import { TranferRecievedService } from '../service/tranfer-recieved.service';

@Injectable({ providedIn: 'root' })
export class TranferRecievedRoutingResolveService implements Resolve<ITranferRecieved> {
  constructor(protected service: TranferRecievedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITranferRecieved> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tranferRecieved: HttpResponse<TranferRecieved>) => {
          if (tranferRecieved.body) {
            return of(tranferRecieved.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TranferRecieved());
  }
}
