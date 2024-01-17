import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVisit, NewVisit } from '../visit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVisit for edit and NewVisitFormGroupInput for create.
 */
type VisitFormGroupInput = IVisit | PartialWithRequiredKeyOf<NewVisit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVisit | NewVisit> = Omit<T, 'inTime' | 'outTime'> & {
  inTime?: string | null;
  outTime?: string | null;
};

type VisitFormRawValue = FormValueOf<IVisit>;

type NewVisitFormRawValue = FormValueOf<NewVisit>;

type VisitFormDefaults = Pick<NewVisit, 'id' | 'inTime' | 'outTime'>;

type VisitFormGroupContent = {
  id: FormControl<VisitFormRawValue['id'] | NewVisit['id']>;
  inTime: FormControl<VisitFormRawValue['inTime']>;
  outTime: FormControl<VisitFormRawValue['outTime']>;
  carRegistrationNumber: FormControl<VisitFormRawValue['carRegistrationNumber']>;
  carParkingNumber: FormControl<VisitFormRawValue['carParkingNumber']>;
  messageToHost: FormControl<VisitFormRawValue['messageToHost']>;
  visitor: FormControl<VisitFormRawValue['visitor']>;
  office: FormControl<VisitFormRawValue['office']>;
  host: FormControl<VisitFormRawValue['host']>;
};

export type VisitFormGroup = FormGroup<VisitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VisitFormService {
  createVisitFormGroup(visit: VisitFormGroupInput = { id: null }): VisitFormGroup {
    const visitRawValue = this.convertVisitToVisitRawValue({
      ...this.getFormDefaults(),
      ...visit,
    });
    return new FormGroup<VisitFormGroupContent>({
      id: new FormControl(
        { value: visitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      inTime: new FormControl(visitRawValue.inTime),
      outTime: new FormControl(visitRawValue.outTime),
      carRegistrationNumber: new FormControl(visitRawValue.carRegistrationNumber),
      carParkingNumber: new FormControl(visitRawValue.carParkingNumber),
      messageToHost: new FormControl(visitRawValue.messageToHost),
      visitor: new FormControl(visitRawValue.visitor, {
        validators: [Validators.required],
      }),
      office: new FormControl(visitRawValue.office, {
        validators: [Validators.required],
      }),
      host: new FormControl(visitRawValue.host, {
        validators: [Validators.required],
      }),
    });
  }

  getVisit(form: VisitFormGroup): IVisit | NewVisit {
    return this.convertVisitRawValueToVisit(form.getRawValue() as VisitFormRawValue | NewVisitFormRawValue);
  }

  resetForm(form: VisitFormGroup, visit: VisitFormGroupInput): void {
    const visitRawValue = this.convertVisitToVisitRawValue({ ...this.getFormDefaults(), ...visit });
    form.reset(
      {
        ...visitRawValue,
        id: { value: visitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VisitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      inTime: currentTime,
      outTime: currentTime,
    };
  }

  private convertVisitRawValueToVisit(rawVisit: VisitFormRawValue | NewVisitFormRawValue): IVisit | NewVisit {
    return {
      ...rawVisit,
      inTime: dayjs(rawVisit.inTime, DATE_TIME_FORMAT),
      outTime: dayjs(rawVisit.outTime, DATE_TIME_FORMAT),
    };
  }

  private convertVisitToVisitRawValue(
    visit: IVisit | (Partial<NewVisit> & VisitFormDefaults),
  ): VisitFormRawValue | PartialWithRequiredKeyOf<NewVisitFormRawValue> {
    return {
      ...visit,
      inTime: visit.inTime ? visit.inTime.format(DATE_TIME_FORMAT) : undefined,
      outTime: visit.outTime ? visit.outTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
