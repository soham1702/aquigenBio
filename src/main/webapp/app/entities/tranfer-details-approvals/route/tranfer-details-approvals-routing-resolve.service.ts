import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITranferDetailsApprovals, TranferDetailsApprovals } from '../tranfer-details-approvals.model';
import { TranferDetailsApprovalsService } from '../service/tranfer-details-approvals.service';

@Injectable({ providedIn: 'root' })
export class TranferDetailsApprovalsRoutingResolveService implements Resolve<ITranferDetailsApprovals> {
  constructor(protected service: TranferDetailsApprovalsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITranferDetailsApprovals> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tranferDetailsApprovals: HttpResponse<TranferDetailsApprovals>) => {
          if (tranferDetailsApprovals.body) {
            return of(tranferDetailsApprovals.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TranferDetailsApprovals());
  }
}
