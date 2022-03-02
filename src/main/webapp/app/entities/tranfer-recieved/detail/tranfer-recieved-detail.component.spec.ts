import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TranferRecievedDetailComponent } from './tranfer-recieved-detail.component';

describe('TranferRecieved Management Detail Component', () => {
  let comp: TranferRecievedDetailComponent;
  let fixture: ComponentFixture<TranferRecievedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TranferRecievedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tranferRecieved: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TranferRecievedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TranferRecievedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tranferRecieved on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tranferRecieved).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
