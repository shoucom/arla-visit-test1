import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VisitorDetailComponent } from './visitor-detail.component';

describe('Visitor Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisitorDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VisitorDetailComponent,
              resolve: { visitor: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VisitorDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load visitor on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VisitorDetailComponent);

      // THEN
      expect(instance.visitor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
