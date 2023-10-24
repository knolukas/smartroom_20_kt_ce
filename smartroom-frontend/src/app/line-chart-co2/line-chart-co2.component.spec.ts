import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Co2LineChartComponent } from './line-chart-co2.component';

describe('LineChartComponent', () => {
  let component: Co2LineChartComponent;
  let fixture: ComponentFixture<Co2LineChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Co2LineChartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Co2LineChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
