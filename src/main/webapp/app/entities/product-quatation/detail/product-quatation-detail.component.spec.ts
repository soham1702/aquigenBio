import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductQuatationDetailComponent } from './product-quatation-detail.component';

describe('ProductQuatation Management Detail Component', () => {
  let comp: ProductQuatationDetailComponent;
  let fixture: ComponentFixture<ProductQuatationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductQuatationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productQuatation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductQuatationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductQuatationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productQuatation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productQuatation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
