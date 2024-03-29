import dayjs from 'dayjs/esm';
import { IVisitor } from 'app/entities/visitor/visitor.model';
import { IOffice } from 'app/entities/office/office.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IVisit {
  id: number;
  inTime?: dayjs.Dayjs | null;
  outTime?: dayjs.Dayjs | null;
  carRegistrationNumber?: string | null;
  carParkingNumber?: string | null;
  messageToHost?: string | null;
  visitor?: IVisitor | null;
  office?: IOffice | null;
  host?: IEmployee | null;
}

export type NewVisit = Omit<IVisit, 'id'> & { id: null };
