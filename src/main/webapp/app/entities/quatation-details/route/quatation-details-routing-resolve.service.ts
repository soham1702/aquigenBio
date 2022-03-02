import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQuatationDetails, QuatationDetails } from '../quatation-details.model';
import { QuatationDetailsService } from '../service/quatation-details.service';

@Injectable({ providedIn: 'root' })
export class QuatationDetailsRoutingResolveService implements Resolve<IQuatationDetails> {
  constructor(protected service: QuatationDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuatationDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((quatationDetails: HttpResponse<QuatationDetails>) => {
          if (quatationDetails.body) {
            return of(quatationDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuatationDetails());
  }
}
