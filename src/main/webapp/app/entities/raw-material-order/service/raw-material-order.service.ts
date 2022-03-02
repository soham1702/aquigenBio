import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRawMaterialOrder, getRawMaterialOrderIdentifier } from '../raw-material-order.model';

export type EntityResponseType = HttpResponse<IRawMaterialOrder>;
export type EntityArrayResponseType = HttpResponse<IRawMaterialOrder[]>;

@Injectable({ providedIn: 'root' })
export class RawMaterialOrderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/raw-material-orders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rawMaterialOrder: IRawMaterialOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialOrder);
    return this.http
      .post<IRawMaterialOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rawMaterialOrder: IRawMaterialOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialOrder);
    return this.http
      .put<IRawMaterialOrder>(`${this.resourceUrl}/${getRawMaterialOrderIdentifier(rawMaterialOrder) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rawMaterialOrder: IRawMaterialOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialOrder);
    return this.http
      .patch<IRawMaterialOrder>(`${this.resourceUrl}/${getRawMaterialOrderIdentifier(rawMaterialOrder) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRawMaterialOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRawMaterialOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRawMaterialOrderToCollectionIfMissing(
    rawMaterialOrderCollection: IRawMaterialOrder[],
    ...rawMaterialOrdersToCheck: (IRawMaterialOrder | null | undefined)[]
  ): IRawMaterialOrder[] {
    const rawMaterialOrders: IRawMaterialOrder[] = rawMaterialOrdersToCheck.filter(isPresent);
    if (rawMaterialOrders.length > 0) {
      const rawMaterialOrderCollectionIdentifiers = rawMaterialOrderCollection.map(
        rawMaterialOrderItem => getRawMaterialOrderIdentifier(rawMaterialOrderItem)!
      );
      const rawMaterialOrdersToAdd = rawMaterialOrders.filter(rawMaterialOrderItem => {
        const rawMaterialOrderIdentifier = getRawMaterialOrderIdentifier(rawMaterialOrderItem);
        if (rawMaterialOrderIdentifier == null || rawMaterialOrderCollectionIdentifiers.includes(rawMaterialOrderIdentifier)) {
          return false;
        }
        rawMaterialOrderCollectionIdentifiers.push(rawMaterialOrderIdentifier);
        return true;
      });
      return [...rawMaterialOrdersToAdd, ...rawMaterialOrderCollection];
    }
    return rawMaterialOrderCollection;
  }

  protected convertDateFromClient(rawMaterialOrder: IRawMaterialOrder): IRawMaterialOrder {
    return Object.assign({}, rawMaterialOrder, {
      deliveryDate: rawMaterialOrder.deliveryDate?.isValid() ? rawMaterialOrder.deliveryDate.toJSON() : undefined,
      orderedOn: rawMaterialOrder.orderedOn?.isValid() ? rawMaterialOrder.orderedOn.toJSON() : undefined,
      lastModified: rawMaterialOrder.lastModified?.isValid() ? rawMaterialOrder.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deliveryDate = res.body.deliveryDate ? dayjs(res.body.deliveryDate) : undefined;
      res.body.orderedOn = res.body.orderedOn ? dayjs(res.body.orderedOn) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rawMaterialOrder: IRawMaterialOrder) => {
        rawMaterialOrder.deliveryDate = rawMaterialOrder.deliveryDate ? dayjs(rawMaterialOrder.deliveryDate) : undefined;
        rawMaterialOrder.orderedOn = rawMaterialOrder.orderedOn ? dayjs(rawMaterialOrder.orderedOn) : undefined;
        rawMaterialOrder.lastModified = rawMaterialOrder.lastModified ? dayjs(rawMaterialOrder.lastModified) : undefined;
      });
    }
    return res;
  }
}
