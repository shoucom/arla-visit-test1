import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 19297,
  firstName: 'Andreane',
  lastName: 'Doyle',
};

export const sampleWithPartialData: IEmployee = {
  id: 1063,
  firstName: 'Jamir',
  lastName: 'Corwin',
  pin: 20805,
  email: 'Winona55@gmail.com',
  mobileNumber: 4358,
};

export const sampleWithFullData: IEmployee = {
  id: 20662,
  firstName: 'Abbie',
  lastName: 'Dicki',
  pin: 10149,
  email: 'Taya.Tremblay8@yahoo.com',
  mobileNumber: 22858,
  jobTitle: 'Chief Response Specialist',
};

export const sampleWithNewData: NewEmployee = {
  firstName: 'Crystal',
  lastName: 'Rice',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
