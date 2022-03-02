import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuatationDetailsDetailComponent } from './quatation-details-detail.component';

describe('QuatationDetails Management Detail Component', () => {
  let comp: QuatationDetailsDetailComponent;
  let fixture: ComponentFixture<QuatationDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuatationDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ quatationDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QuatationDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QuatationDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load quatationDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.quatationDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
