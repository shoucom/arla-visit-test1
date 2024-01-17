import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VisitDetailComponent } from './visit-detail.component';

describe('Visit Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisitDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VisitDetailComponent,
              resolve: { visit: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VisitDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load visit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VisitDetailComponent);

      // THEN
      expect(instance.visit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
