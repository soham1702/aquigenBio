import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranferDetailsApprovals } from '../tranfer-details-approvals.model';

@Component({
  selector: 'jhi-tranfer-details-approvals-detail',
  templateUrl: './tranfer-details-approvals-detail.component.html',
})
export class TranferDetailsApprovalsDetailComponent implements OnInit {
  tranferDetailsApprovals: ITranferDetailsApprovals | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tranferDetailsApprovals }) => {
      this.tranferDetailsApprovals = tranferDetailsApprovals;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
