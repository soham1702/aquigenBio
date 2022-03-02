import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductQuatationService } from '../service/product-quatation.service';
import { IProductQuatation, ProductQuatation } from '../product-quatation.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { ProductQuatationUpdateComponent } from './product-quatation-update.component';

describe('ProductQuatation Management Update Component', () => {
  let comp: ProductQuatationUpdateComponent;
  let fixture: ComponentFixture<ProductQuatationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productQuatationService: ProductQuatationService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductQuatationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProductQuatationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductQuatationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productQuatationService = TestBed.inject(ProductQuatationService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call securityUser query and add missing value', () => {
      const productQuatation: IProductQuatation = { id: 456 };
      const securityUser: ISecurityUser = { id: 9657 };
      productQuatation.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 52225 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const expectedCollection: ISecurityUser[] = [securityUser, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productQuatation });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(securityUserCollection, securityUser);
      expect(comp.securityUsersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productQuatation: IProductQuatation = { id: 456 };
      const securityUser: ISecurityUser = { id: 60884 };
      productQuatation.securityUser = securityUser;

      activatedRoute.data = of({ productQuatation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productQuatation));
      expect(comp.securityUsersCollection).toContain(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductQuatation>>();
      const productQuatation = { id: 123 };
      jest.spyOn(productQuatationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productQuatation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productQuatation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productQuatationService.update).toHaveBeenCalledWith(productQuatation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductQuatation>>();
      const productQuatation = new ProductQuatation();
      jest.spyOn(productQuatationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productQuatation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productQuatation }));
      saveSubject.complete();

      // THEN
      expect(productQuatationService.create).toHaveBeenCalledWith(productQuatation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductQuatation>>();
      const productQuatation = { id: 123 };
      jest.spyOn(productQuatationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productQuatation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productQuatationService.update).toHaveBeenCalledWith(productQuatation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
