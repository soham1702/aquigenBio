import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductQuatation, getProductQuatationIdentifier } from '../product-quatation.model';

export type EntityResponseType = HttpResponse<IProductQuatation>;
export type EntityArrayResponseType = HttpResponse<IProductQuatation[]>;

@Injectable({ providedIn: 'root' })
export class ProductQuatationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-quatations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productQuatation: IProductQuatation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productQuatation);
    return this.http
      .post<IProductQuatation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productQuatation: IProductQuatation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productQuatation);
    return this.http
      .put<IProductQuatation>(`${this.resourceUrl}/${getProductQuatationIdentifier(productQuatation) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(productQuatation: IProductQuatation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productQuatation);
    return this.http
      .patch<IProductQuatation>(`${this.resourceUrl}/${getProductQuatationIdentifier(productQuatation) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductQuatation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductQuatation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductQuatationToCollectionIfMissing(
    productQuatationCollection: IProductQuatation[],
    ...productQuatationsToCheck: (IProductQuatation | null | undefined)[]
  ): IProductQuatation[] {
    const productQuatations: IProductQuatation[] = productQuatationsToCheck.filter(isPresent);
    if (productQuatations.length > 0) {
      const productQuatationCollectionIdentifiers = productQuatationCollection.map(
        productQuatationItem => getProductQuatationIdentifier(productQuatationItem)!
      );
      const productQuatationsToAdd = productQuatations.filter(productQuatationItem => {
        const productQuatationIdentifier = getProductQuatationIdentifier(productQuatationItem);
        if (productQuatationIdentifier == null || productQuatationCollectionIdentifiers.includes(productQuatationIdentifier)) {
          return false;
        }
        productQuatationCollectionIdentifiers.push(productQuatationIdentifier);
        return true;
      });
      return [...productQuatationsToAdd, ...productQuatationCollection];
    }
    return productQuatationCollection;
  }

  protected convertDateFromClient(productQuatation: IProductQuatation): IProductQuatation {
    return Object.assign({}, productQuatation, {
      quatationdate: productQuatation.quatationdate?.isValid() ? productQuatation.quatationdate.toJSON() : undefined,
      expectedDelivery: productQuatation.expectedDelivery?.isValid() ? productQuatation.expectedDelivery.toJSON() : undefined,
      quoValidity: productQuatation.quoValidity?.isValid() ? productQuatation.quoValidity.toJSON() : undefined,
      lastModified: productQuatation.lastModified?.isValid() ? productQuatation.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.quatationdate = res.body.quatationdate ? dayjs(res.body.quatationdate) : undefined;
      res.body.expectedDelivery = res.body.expectedDelivery ? dayjs(res.body.expectedDelivery) : undefined;
      res.body.quoValidity = res.body.quoValidity ? dayjs(res.body.quoValidity) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productQuatation: IProductQuatation) => {
        productQuatation.quatationdate = productQuatation.quatationdate ? dayjs(productQuatation.quatationdate) : undefined;
        productQuatation.expectedDelivery = productQuatation.expectedDelivery ? dayjs(productQuatation.expectedDelivery) : undefined;
        productQuatation.quoValidity = productQuatation.quoValidity ? dayjs(productQuatation.quoValidity) : undefined;
        productQuatation.lastModified = productQuatation.lastModified ? dayjs(productQuatation.lastModified) : undefined;
      });
    }
    return res;
  }
}
