import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITranferDetailsApprovals, getTranferDetailsApprovalsIdentifier } from '../tranfer-details-approvals.model';

export type EntityResponseType = HttpResponse<ITranferDetailsApprovals>;
export type EntityArrayResponseType = HttpResponse<ITranferDetailsApprovals[]>;

@Injectable({ providedIn: 'root' })
export class TranferDetailsApprovalsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tranfer-details-approvals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tranferDetailsApprovals: ITranferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferDetailsApprovals);
    return this.http
      .post<ITranferDetailsApprovals>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tranferDetailsApprovals: ITranferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferDetailsApprovals);
    return this.http
      .put<ITranferDetailsApprovals>(
        `${this.resourceUrl}/${getTranferDetailsApprovalsIdentifier(tranferDetailsApprovals) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tranferDetailsApprovals: ITranferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferDetailsApprovals);
    return this.http
      .patch<ITranferDetailsApprovals>(
        `${this.resourceUrl}/${getTranferDetailsApprovalsIdentifier(tranferDetailsApprovals) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITranferDetailsApprovals>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITranferDetailsApprovals[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTranferDetailsApprovalsToCollectionIfMissing(
    tranferDetailsApprovalsCollection: ITranferDetailsApprovals[],
    ...tranferDetailsApprovalsToCheck: (ITranferDetailsApprovals | null | undefined)[]
  ): ITranferDetailsApprovals[] {
    const tranferDetailsApprovals: ITranferDetailsApprovals[] = tranferDetailsApprovalsToCheck.filter(isPresent);
    if (tranferDetailsApprovals.length > 0) {
      const tranferDetailsApprovalsCollectionIdentifiers = tranferDetailsApprovalsCollection.map(
        tranferDetailsApprovalsItem => getTranferDetailsApprovalsIdentifier(tranferDetailsApprovalsItem)!
      );
      const tranferDetailsApprovalsToAdd = tranferDetailsApprovals.filter(tranferDetailsApprovalsItem => {
        const tranferDetailsApprovalsIdentifier = getTranferDetailsApprovalsIdentifier(tranferDetailsApprovalsItem);
        if (
          tranferDetailsApprovalsIdentifier == null ||
          tranferDetailsApprovalsCollectionIdentifiers.includes(tranferDetailsApprovalsIdentifier)
        ) {
          return false;
        }
        tranferDetailsApprovalsCollectionIdentifiers.push(tranferDetailsApprovalsIdentifier);
        return true;
      });
      return [...tranferDetailsApprovalsToAdd, ...tranferDetailsApprovalsCollection];
    }
    return tranferDetailsApprovalsCollection;
  }

  protected convertDateFromClient(tranferDetailsApprovals: ITranferDetailsApprovals): ITranferDetailsApprovals {
    return Object.assign({}, tranferDetailsApprovals, {
      approvalDate: tranferDetailsApprovals.approvalDate?.isValid() ? tranferDetailsApprovals.approvalDate.toJSON() : undefined,
      lastModified: tranferDetailsApprovals.lastModified?.isValid() ? tranferDetailsApprovals.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.approvalDate = res.body.approvalDate ? dayjs(res.body.approvalDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tranferDetailsApprovals: ITranferDetailsApprovals) => {
        tranferDetailsApprovals.approvalDate = tranferDetailsApprovals.approvalDate
          ? dayjs(tranferDetailsApprovals.approvalDate)
          : undefined;
        tranferDetailsApprovals.lastModified = tranferDetailsApprovals.lastModified
          ? dayjs(tranferDetailsApprovals.lastModified)
          : undefined;
      });
    }
    return res;
  }
}
