import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRawMaterialOrder } from '../raw-material-order.model';

@Component({
  selector: 'jhi-raw-material-order-detail',
  templateUrl: './raw-material-order-detail.component.html',
})
export class RawMaterialOrderDetailComponent implements OnInit {
  rawMaterialOrder: IRawMaterialOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterialOrder }) => {
      this.rawMaterialOrder = rawMaterialOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
