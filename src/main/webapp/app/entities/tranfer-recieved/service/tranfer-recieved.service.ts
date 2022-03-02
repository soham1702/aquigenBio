import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITranferRecieved, getTranferRecievedIdentifier } from '../tranfer-recieved.model';

export type EntityResponseType = HttpResponse<ITranferRecieved>;
export type EntityArrayResponseType = HttpResponse<ITranferRecieved[]>;

@Injectable({ providedIn: 'root' })
export class TranferRecievedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tranfer-recieveds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tranferRecieved: ITranferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferRecieved);
    return this.http
      .post<ITranferRecieved>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tranferRecieved: ITranferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferRecieved);
    return this.http
      .put<ITranferRecieved>(`${this.resourceUrl}/${getTranferRecievedIdentifier(tranferRecieved) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tranferRecieved: ITranferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tranferRecieved);
    return this.http
      .patch<ITranferRecieved>(`${this.resourceUrl}/${getTranferRecievedIdentifier(tranferRecieved) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITranferRecieved>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITranferRecieved[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTranferRecievedToCollectionIfMissing(
    tranferRecievedCollection: ITranferRecieved[],
    ...tranferRecievedsToCheck: (ITranferRecieved | null | undefined)[]
  ): ITranferRecieved[] {
    const tranferRecieveds: ITranferRecieved[] = tranferRecievedsToCheck.filter(isPresent);
    if (tranferRecieveds.length > 0) {
      const tranferRecievedCollectionIdentifiers = tranferRecievedCollection.map(
        tranferRecievedItem => getTranferRecievedIdentifier(tranferRecievedItem)!
      );
      const tranferRecievedsToAdd = tranferRecieveds.filter(tranferRecievedItem => {
        const tranferRecievedIdentifier = getTranferRecievedIdentifier(tranferRecievedItem);
        if (tranferRecievedIdentifier == null || tranferRecievedCollectionIdentifiers.includes(tranferRecievedIdentifier)) {
          return false;
        }
        tranferRecievedCollectionIdentifiers.push(tranferRecievedIdentifier);
        return true;
      });
      return [...tranferRecievedsToAdd, ...tranferRecievedCollection];
    }
    return tranferRecievedCollection;
  }

  protected convertDateFromClient(tranferRecieved: ITranferRecieved): ITranferRecieved {
    return Object.assign({}, tranferRecieved, {
      transferDate: tranferRecieved.transferDate?.isValid() ? tranferRecieved.transferDate.toJSON() : undefined,
      lastModified: tranferRecieved.lastModified?.isValid() ? tranferRecieved.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transferDate = res.body.transferDate ? dayjs(res.body.transferDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tranferRecieved: ITranferRecieved) => {
        tranferRecieved.transferDate = tranferRecieved.transferDate ? dayjs(tranferRecieved.transferDate) : undefined;
        tranferRecieved.lastModified = tranferRecieved.lastModified ? dayjs(tranferRecieved.lastModified) : undefined;
      });
    }
    return res;
  }
}
