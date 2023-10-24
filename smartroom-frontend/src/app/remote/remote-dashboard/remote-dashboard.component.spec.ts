import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoteDashboardComponent } from './remote-dashboard.component';

describe('RemoteDashboardComponent', () => {
  let component: RemoteDashboardComponent;
  let fixture: ComponentFixture<RemoteDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoteDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoteDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
