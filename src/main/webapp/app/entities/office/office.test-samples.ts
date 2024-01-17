import { IOffice, NewOffice } from './office.model';

export const sampleWithRequiredData: IOffice = {
  id: 10772,
  name: 'dynasty',
};

export const sampleWithPartialData: IOffice = {
  id: 9258,
  name: 'malice',
  timeZone: 'brook',
};

export const sampleWithFullData: IOffice = {
  id: 16893,
  name: 'given',
  address: 'superimpose spot gleefully',
  phone: 9821,
  timeZone: 'quirky quirkily',
  wifiPassword: 'joyful whereas for',
  language: 'ENGLISH',
};

export const sampleWithNewData: NewOffice = {
  name: 'yowza narrow atop',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
