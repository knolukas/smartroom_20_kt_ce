import { Component, OnInit, Input } from '@angular/core';
import Chart, { ChartDataset } from 'chart.js/auto';
import { Fan, Light, Fenster, Door, TemperatureData, Co2SensorData} from "../entities/entity";
import { Room } from '../entities/entity';
interface DataPoint {
  x: string;
  y: number;
}

@Component({
  selector: 'line-chart-co2',
  templateUrl: './line-chart-co2.component.html',
  styleUrls: ['./line-chart-co2.component.scss']
})

export class Co2LineChartComponent {

  public chart: any;
  public readonly yAxisMin = 0;
  public readonly yAxisMax = 100;
  public readonly yStep = 1;
  public typeOfChart: String = "ppm";
  private temperatureData: DataPoint[] = [];

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

    this.chart = new Chart("MyChartCo2", {
      type: 'line',
      data: {
        datasets: [{
            tension: 0.3,
            label: "Co2 Data",
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
            text: "Room Air Quality",
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
                return Number(value).toFixed(2).slice(0,-1) + "ppm";
              }
            },
            grid: {
              color: "rgba(255,255,255,0.15)"
            }
          },
          x: {
              /*afterBuildTicks: (a) => (a.ticks = [
              {
                value: 2
              }, {
                value: 50
              }, {
                value: 80
              }, {
                value: 110
              }]),*/
              ticks: {
                count: 500,
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

  @Input() room_data_line_co2: any;

  public curTime : Date = new Date();
  public currentTimestamp = (new Date(this.curTime.getTime()).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
  public lastCo2Value: any;

  ngOnInit(): void {


   /* if(this.room_data_line_co2.co2SensorData[this.room_data_line_co2.co2SensorData.length - i].cO2value != this.lastCo2Value)
    {
      this.lastCo2Value = this.room_data_line_co2.co2SensorData[this.room_data_line_co2.co2SensorData.length - i].cO2value;
      this.pushData(this.lastCo2Value, this.room_data_line_co2.co2SensorData[this.room_data_line_co2.co2SensorData.length - i].timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' }));
    }*/
  

    this?.createChart();
  }


   /**
    * Check for data update
    * @date 7/3/2023 - 12:08:44 AM
    *
    * @public
    */
   public updateChart() {

    if(this.room_data_line_co2){
    //console.log("push Data!" + this.room_data_line_co2.co2SensorData[this.room_data_line_co2.co2SensorData.length - 1].cO2value);
    const co2Value = this.room_data_line_co2.co2SensorData[this.room_data_line_co2.co2SensorData.length - 1].cO2value;
    const currentTimestamp = (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
    const newDataPoint = { x: currentTimestamp, y: co2Value };

    if(this.chart.data.labels.last != this.currentTimestamp || co2Value != this.lastCo2Value/*this.room_data_line_co2.temperaturData[this.room_data_line_co2.temperaturData.length - 1].temperatureValue.toString()*/ )
    {
      this.lastCo2Value = co2Value;
      this.pushData(co2Value, currentTimestamp);
    }
  }
  }

   /**
    * Add new Datapoint
    * @date 7/3/2023 - 12:08:44 AM
    *
    * @public
    */
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
}



