import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RawMaterialOrderDetailComponent } from './raw-material-order-detail.component';

describe('RawMaterialOrder Management Detail Component', () => {
  let comp: RawMaterialOrderDetailComponent;
  let fixture: ComponentFixture<RawMaterialOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RawMaterialOrderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rawMaterialOrder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RawMaterialOrderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RawMaterialOrderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rawMaterialOrder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rawMaterialOrder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
