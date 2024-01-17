import { IEmployee } from 'app/entities/employee/employee.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IOffice {
  id: number;
  name?: string | null;
  address?: string | null;
  phone?: number | null;
  timeZone?: string | null;
  wifiPassword?: string | null;
  language?: keyof typeof Language | null;
  employees?: IEmployee[] | null;
}

export type NewOffice = Omit<IOffice, 'id'> & { id: null };
