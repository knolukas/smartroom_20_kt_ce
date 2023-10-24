import { Component, OnInit, Input } from '@angular/core';
import Chart, { ChartDataset } from 'chart.js/auto';
import {Room, Fan, Light, Fenster, Door, TemperatureData, Co2SensorData} from "../entities/entity";
interface DataPoint {
  x: string;
  y: number;
}

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})

export class LineChartComponent {

  public chart: any;
  public readonly yAxisMin = 16;
  public readonly yAxisMax = 26;
  public readonly yStep = 1;
  public typeOfChart: String = "°C";
  private temperatureData: DataPoint[] = [
   /* { x: '05:30:10 PM', y: 22 },
    { x: '2022-05-11', y: 25 },
    { x: '2022-05-12', y: 24 },
    { x: '2022-05-13', y: 23 },
    { x: '2022-05-14', y: 25 },
    { x: '2022-05-15', y: 27 },
    { x: '2022-05-16', y: 26 },
    { x: '2022-05-17', y: 24 },
    { x: '2022-05-18', y: 22 },
    { x: '2022-05-19', y: 25 },
    { x: '2022-05-20', y: 24 },
    { x: '2022-05-21', y: 23 },
    { x: '2022-05-22', y: 25 },
    { x: '2022-05-23', y: 27 },
    { x: '2022-05-24', y: 26 },
    { x: '2022-05-25', y: 24 },
    { x: '2022-05-26', y: 22 },
    { x: '2022-05-27', y: 25 },
    { x: '2022-05-28', y: 24 },
    { x: '2022-05-29', y: 23 },
    { x: '2022-05-30', y: 25 },
*/
  ];

  constructor() {

  }

  createChart(){

    const colors = {
      purple: {
        default: "rgba(149, 76, 233, 1)",
        half: "rgba(149, 76, 233, 0.5)",
        quarter: "rgba(149, 76, 233, 0.25)",
        zero: "rgba(149, 76, 233, 0)"
      },
      indigo: {
        default: "rgba(80, 102, 120, 1)",
        quarter: "rgba(80, 102, 120, 0.25)"
      }
    };

    this.chart = new Chart("MyChart", {
      type: 'line',
      data: {
        datasets: [{
            tension: 0.3,
            label: "Temperature",
            borderColor: "white",
            backgroundColor: "white",

            fill: false,
            /*data: [
              { x: '2022-05-10', y: 22 }
            ],*/
            data: this.temperatureData,
            borderWidth: 2,
            pointRadius: 0,
          }
        ]
      },
      options: {
        plugins: {
          title: {
            display: true,
            text: "Room Temperature",
            font: {
              family: "HelveticaNeueExtended",
              weight: "bold",
              size: 32,

            },
            position: "top",
            align: "start",
            color: 'white',
          },
          legend: {
            display: false,
            labels: {
              color: 'White',
              padding: 10,
              font: {
                family: "HelveticaNeueExtended",
                size: 12
              },
            }
          }
        },
        scales: {
          y:{
            ticks: {
              //stepSize: 1,
              maxTicksLimit: 8,
              color: 'White',
              padding: 10,
              font: {
                family: "HelveticaNeueExtended",
                size: 12
              },
              callback: function(value, index, values, ) {
                return value + "°C";
              }
            },
            grid: {
              color: "rgba(255,255,255,0.15)"
            }
          },
          x: {
              afterBuildTicks: (a) => (a.ticks = [
              {
                value: 2
              }, {
                value: 5
              }, {
                value: 8
              }, {
                value: 11
              }]),
              ticks: {
                count: 5,
                autoSkip: false,
                stepSize: 5,
                color: 'White',
                padding: 10,
                font: {
                  family: "HelveticaNeueExtended",
                  size: 12
                }
              }
            /*ticks: {
              autoSkip: false,
              stepSize: 5,
              color: 'White',
              padding: 10,
              font: {
                family: "HelveticaNeueExtended",
                size: 12
              }
            }*/
              /*,
              callback: function(value, index, values) {
                return value + "am";
              }*/

          },
        },
        animation: {
          duration: 0,
          easing: 'linear',
        }
      }
    });
  }

  @Input() room_data_line: any;

  ngOnInit(): void {

    var curTime : Date = new Date();
    for(var i:number = 15; i > 0; i = i - 1){
      const randomTemperature = 0;
      const currentTimestamp = (new Date(curTime.getTime()- 15000 + (1000 * i)).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
      const newDataPoint = { x: currentTimestamp, y: randomTemperature };
    this.temperatureData.push(newDataPoint);
    }

    this.createChart();
    setInterval(() => this.updateChart(), 1000);
  }

   //const currentTimestamp = (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();


   private updateChart() {
    const randomTemperature = this.getRandomTemperature();
    const currentTimestamp = (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();

    const newDataPoint = { x: currentTimestamp, y: randomTemperature };

  //  this.temperatureData.push(newDataPoint);

  try {
    value: Number = this.room_data_line.last.Co2SensorData;
    this.typeOfChart = "°C";
    if(this.chart.data.labels.last.toString() != this.room_data_line.last.timestamp.toString()){
      this.pushData(Number.toString(), this.room_data_line.last.timestamp.toString());
    }
  } catch (error) {
    try{
      value: Number = this.room_data_line.last.Co2SensorData;
      this.typeOfChart = "ppm";
      if(this.chart.data.labels.last.toString() != this.room_data_line.last.timestamp.toString()){
        this.pushData(Number.toString(), this.room_data_line.last.timestamp.toString());
      }
    }catch (error){
      this.typeOfChart = "°C";
      this.pushData(Math.floor(Math.random() * 8 + 15).toString(), (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString());
    }
  }

  }

  public pushData(newDataPoint: String, currentTimestamp: String){
    this.chart.data.datasets[0].data.push(newDataPoint);
    this.chart.data.labels.push(currentTimestamp);

      if (this.chart.data.datasets[0].data.length > 13) {
        this.chart.data.datasets[0].data.shift();
        this.chart.data.labels.shift();
      }

      this.chart.data.datasets[0].data = this.temperatureData;
      this.chart.update();
  }


  private getRandomTemperature() {
    // cO2value: number;temperaturValue: number;
    //TemperaturData Co2SensorData

    return Math.floor(Math.random() * 8 + 15).toString();
  }

    /*if(this.room_data_line.last instanceof TemperaturData){
      if(this.room_data_line.length > 0 && this.room_data_line.last != this.chart.dataset[0].data.last){
        return this.room_data_line.last;
      }
    }*/



}



