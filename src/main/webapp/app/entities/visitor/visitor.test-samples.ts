import { IVisitor, NewVisitor } from './visitor.model';

export const sampleWithRequiredData: IVisitor = {
  id: 10117,
  firstName: 'Isobel',
  lastName: 'Haley',
  mobileNumber: 25760,
};

export const sampleWithPartialData: IVisitor = {
  id: 14276,
  firstName: 'Juston',
  lastName: 'Stamm',
  mobileNumber: 4054,
  company: 'inasmuch',
};

export const sampleWithFullData: IVisitor = {
  id: 32179,
  firstName: 'Genoveva',
  lastName: 'Moen',
  email: 'Walter_Langworth41@gmail.com',
  mobileNumber: 16204,
  company: 'ball bespatter',
};

export const sampleWithNewData: NewVisitor = {
  firstName: 'Amya',
  lastName: 'Reynolds',
  mobileNumber: 28206,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
