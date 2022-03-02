import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WarehouseService } from '../service/warehouse.service';
import { IWarehouse, Warehouse } from '../warehouse.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { WarehouseUpdateComponent } from './warehouse-update.component';

describe('Warehouse Management Update Component', () => {
  let comp: WarehouseUpdateComponent;
  let fixture: ComponentFixture<WarehouseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let warehouseService: WarehouseService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WarehouseUpdateComponent],
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
      .overrideTemplate(WarehouseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WarehouseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    warehouseService = TestBed.inject(WarehouseService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const warehouse: IWarehouse = { id: 456 };
      const securityUsers: ISecurityUser[] = [{ id: 64350 }];
      warehouse.securityUsers = securityUsers;

      const securityUserCollection: ISecurityUser[] = [{ id: 28350 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [...securityUsers];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ warehouse });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const warehouse: IWarehouse = { id: 456 };
      const securityUsers: ISecurityUser = { id: 15062 };
      warehouse.securityUsers = [securityUsers];

      activatedRoute.data = of({ warehouse });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(warehouse));
      expect(comp.securityUsersSharedCollection).toContain(securityUsers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Warehouse>>();
      const warehouse = { id: 123 };
      jest.spyOn(warehouseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ warehouse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: warehouse }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(warehouseService.update).toHaveBeenCalledWith(warehouse);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Warehouse>>();
      const warehouse = new Warehouse();
      jest.spyOn(warehouseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ warehouse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: warehouse }));
      saveSubject.complete();

      // THEN
      expect(warehouseService.create).toHaveBeenCalledWith(warehouse);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Warehouse>>();
      const warehouse = { id: 123 };
      jest.spyOn(warehouseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ warehouse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(warehouseService.update).toHaveBeenCalledWith(warehouse);
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

  describe('Getting selected relationships', () => {
    describe('getSelectedSecurityUser', () => {
      it('Should return option if no SecurityUser is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedSecurityUser(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected SecurityUser for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedSecurityUser(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this SecurityUser is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedSecurityUser(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
