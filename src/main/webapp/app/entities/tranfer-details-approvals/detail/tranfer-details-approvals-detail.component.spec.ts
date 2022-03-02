import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TranferDetailsApprovalsDetailComponent } from './tranfer-details-approvals-detail.component';

describe('TranferDetailsApprovals Management Detail Component', () => {
  let comp: TranferDetailsApprovalsDetailComponent;
  let fixture: ComponentFixture<TranferDetailsApprovalsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TranferDetailsApprovalsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tranferDetailsApprovals: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TranferDetailsApprovalsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TranferDetailsApprovalsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tranferDetailsApprovals on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tranferDetailsApprovals).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
