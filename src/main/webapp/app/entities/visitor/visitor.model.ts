export interface IVisitor {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  mobileNumber?: number | null;
  company?: string | null;
}

export type NewVisitor = Omit<IVisitor, 'id'> & { id: null };
