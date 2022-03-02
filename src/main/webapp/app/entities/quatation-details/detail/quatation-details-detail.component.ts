import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuatationDetails } from '../quatation-details.model';

@Component({
  selector: 'jhi-quatation-details-detail',
  templateUrl: './quatation-details-detail.component.html',
})
export class QuatationDetailsDetailComponent implements OnInit {
  quatationDetails: IQuatationDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quatationDetails }) => {
      this.quatationDetails = quatationDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
