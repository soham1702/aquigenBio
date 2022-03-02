import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductQuatation } from '../product-quatation.model';

@Component({
  selector: 'jhi-product-quatation-detail',
  templateUrl: './product-quatation-detail.component.html',
})
export class ProductQuatationDetailComponent implements OnInit {
  productQuatation: IProductQuatation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productQuatation }) => {
      this.productQuatation = productQuatation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
