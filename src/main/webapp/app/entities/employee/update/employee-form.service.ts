import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

type EmployeeFormDefaults = Pick<NewEmployee, 'id' | 'offices'>;

type EmployeeFormGroupContent = {
  id: FormControl<IEmployee['id'] | NewEmployee['id']>;
  firstName: FormControl<IEmployee['firstName']>;
  lastName: FormControl<IEmployee['lastName']>;
  pin: FormControl<IEmployee['pin']>;
  email: FormControl<IEmployee['email']>;
  mobileNumber: FormControl<IEmployee['mobileNumber']>;
  jobTitle: FormControl<IEmployee['jobTitle']>;
  offices: FormControl<IEmployee['offices']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { id: null }): EmployeeFormGroup {
    const employeeRawValue = {
      ...this.getFormDefaults(),
      ...employee,
    };
    return new FormGroup<EmployeeFormGroupContent>({
      id: new FormControl(
        { value: employeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(employeeRawValue.firstName, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(employeeRawValue.lastName, {
        validators: [Validators.required],
      }),
      pin: new FormControl(employeeRawValue.pin),
      email: new FormControl(employeeRawValue.email),
      mobileNumber: new FormControl(employeeRawValue.mobileNumber),
      jobTitle: new FormControl(employeeRawValue.jobTitle),
      offices: new FormControl(employeeRawValue.offices ?? []),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return form.getRawValue() as IEmployee | NewEmployee;
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = { ...this.getFormDefaults(), ...employee };
    form.reset(
      {
        ...employeeRawValue,
        id: { value: employeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    return {
      id: null,
      offices: [],
    };
  }
}
