import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IOffice } from 'app/entities/office/office.model';
import { OfficeService } from 'app/entities/office/service/office.service';
import { EmployeeService } from '../service/employee.service';
import { IEmployee } from '../employee.model';
import { EmployeeFormService } from './employee-form.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Employee Management Update Component', () => {
  let comp: EmployeeUpdateComponent;
  let fixture: ComponentFixture<EmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeFormService: EmployeeFormService;
  let employeeService: EmployeeService;
  let officeService: OfficeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EmployeeUpdateComponent],
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
      .overrideTemplate(EmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeFormService = TestBed.inject(EmployeeFormService);
    employeeService = TestBed.inject(EmployeeService);
    officeService = TestBed.inject(OfficeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Office query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const offices: IOffice[] = [{ id: 14691 }];
      employee.offices = offices;

      const officeCollection: IOffice[] = [{ id: 22714 }];
      jest.spyOn(officeService, 'query').mockReturnValue(of(new HttpResponse({ body: officeCollection })));
      const additionalOffices = [...offices];
      const expectedCollection: IOffice[] = [...additionalOffices, ...officeCollection];
      jest.spyOn(officeService, 'addOfficeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(officeService.query).toHaveBeenCalled();
      expect(officeService.addOfficeToCollectionIfMissing).toHaveBeenCalledWith(
        officeCollection,
        ...additionalOffices.map(expect.objectContaining),
      );
      expect(comp.officesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employee: IEmployee = { id: 456 };
      const office: IOffice = { id: 17050 };
      employee.offices = [office];

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(comp.officesSharedCollection).toContain(office);
      expect(comp.employee).toEqual(employee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue(employee);
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeService.update).toHaveBeenCalledWith(expect.objectContaining(employee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue({ id: null });
      jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(employeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOffice', () => {
      it('Should forward to officeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(officeService, 'compareOffice');
        comp.compareOffice(entity, entity2);
        expect(officeService.compareOffice).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
