import dayjs from 'dayjs/esm';

import { IVisit, NewVisit } from './visit.model';

export const sampleWithRequiredData: IVisit = {
  id: 29759,
};

export const sampleWithPartialData: IVisit = {
  id: 8421,
  inTime: dayjs('2024-01-16T20:39'),
  outTime: dayjs('2024-01-16T20:50'),
  carRegistrationNumber: 'grouse evangelise',
  messageToHost: 'upon ew',
};

export const sampleWithFullData: IVisit = {
  id: 17463,
  inTime: dayjs('2024-01-17T00:18'),
  outTime: dayjs('2024-01-16T22:18'),
  carRegistrationNumber: 'phew upsell',
  carParkingNumber: 'drowse',
  messageToHost: 'brr',
};

export const sampleWithNewData: NewVisit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
