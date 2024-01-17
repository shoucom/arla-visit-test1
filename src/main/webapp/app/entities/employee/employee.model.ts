import { IOffice } from 'app/entities/office/office.model';

export interface IEmployee {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  pin?: number | null;
  email?: string | null;
  mobileNumber?: number | null;
  jobTitle?: string | null;
  offices?: IOffice[] | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
