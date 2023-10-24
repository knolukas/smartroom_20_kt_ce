
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoteDetailsComponent } from './remote-details.component';

describe('RemoteDetailsComponent', () => {
  let component: RemoteDetailsComponent;
  let fixture: ComponentFixture<RemoteDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoteDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
