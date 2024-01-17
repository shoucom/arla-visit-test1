import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'office',
    data: { pageTitle: 'arlaVisitTest1App.office.home.title' },
    loadChildren: () => import('./office/office.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'arlaVisitTest1App.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  {
    path: 'visitor',
    data: { pageTitle: 'arlaVisitTest1App.visitor.home.title' },
    loadChildren: () => import('./visitor/visitor.routes'),
  },
  {
    path: 'visit',
    data: { pageTitle: 'arlaVisitTest1App.visit.home.title' },
    loadChildren: () => import('./visit/visit.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
