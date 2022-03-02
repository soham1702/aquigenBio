import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranferRecieved } from '../tranfer-recieved.model';

@Component({
  selector: 'jhi-tranfer-recieved-detail',
  templateUrl: './tranfer-recieved-detail.component.html',
})
export class TranferRecievedDetailComponent implements OnInit {
  tranferRecieved: ITranferRecieved | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tranferRecieved }) => {
      this.tranferRecieved = tranferRecieved;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
