import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQuatationDetails, getQuatationDetailsIdentifier } from '../quatation-details.model';

export type EntityResponseType = HttpResponse<IQuatationDetails>;
export type EntityArrayResponseType = HttpResponse<IQuatationDetails[]>;

@Injectable({ providedIn: 'root' })
export class QuatationDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/quatation-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(quatationDetails: IQuatationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quatationDetails);
    return this.http
      .post<IQuatationDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(quatationDetails: IQuatationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quatationDetails);
    return this.http
      .put<IQuatationDetails>(`${this.resourceUrl}/${getQuatationDetailsIdentifier(quatationDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(quatationDetails: IQuatationDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quatationDetails);
    return this.http
      .patch<IQuatationDetails>(`${this.resourceUrl}/${getQuatationDetailsIdentifier(quatationDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuatationDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuatationDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQuatationDetailsToCollectionIfMissing(
    quatationDetailsCollection: IQuatationDetails[],
    ...quatationDetailsToCheck: (IQuatationDetails | null | undefined)[]
  ): IQuatationDetails[] {
    const quatationDetails: IQuatationDetails[] = quatationDetailsToCheck.filter(isPresent);
    if (quatationDetails.length > 0) {
      const quatationDetailsCollectionIdentifiers = quatationDetailsCollection.map(
        quatationDetailsItem => getQuatationDetailsIdentifier(quatationDetailsItem)!
      );
      const quatationDetailsToAdd = quatationDetails.filter(quatationDetailsItem => {
        const quatationDetailsIdentifier = getQuatationDetailsIdentifier(quatationDetailsItem);
        if (quatationDetailsIdentifier == null || quatationDetailsCollectionIdentifiers.includes(quatationDetailsIdentifier)) {
          return false;
        }
        quatationDetailsCollectionIdentifiers.push(quatationDetailsIdentifier);
        return true;
      });
      return [...quatationDetailsToAdd, ...quatationDetailsCollection];
    }
    return quatationDetailsCollection;
  }

  protected convertDateFromClient(quatationDetails: IQuatationDetails): IQuatationDetails {
    return Object.assign({}, quatationDetails, {
      lastModified: quatationDetails.lastModified?.isValid() ? quatationDetails.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((quatationDetails: IQuatationDetails) => {
        quatationDetails.lastModified = quatationDetails.lastModified ? dayjs(quatationDetails.lastModified) : undefined;
      });
    }
    return res;
  }
}
