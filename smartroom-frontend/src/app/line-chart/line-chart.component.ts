import { Component, OnInit, Input } from '@angular/core';
import Chart, { ChartDataset } from 'chart.js/auto';
import { Fan, Light, Fenster, Door, TemperatureData, Co2SensorData} from "../entities/entity";
import { Room } from '../entities/entity';
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
  public readonly yAxisMin = -20;
  public readonly yAxisMax = 50;
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
              maxTicksLimit: 8,
              color: 'White',
              padding: 10,
              font: {
                family: "HelveticaNeueExtended",
                size: 12
              },
              callback: function(value, index, values, ) {
                return Number(value).toFixed(2).slice(0,-1) + "°C";
              }
            },
            grid: {
              color: "rgba(255,255,255,0.15)"
            }
          },
          x: {
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

  public curTime : Date = new Date();
  public currentTimestamp = (new Date(this.curTime.getTime()).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
  public lastTemperatureValue: any;

  ngOnInit(): void {

    this.createChart();
  }


   public updateChart() {
    //const temperatureValue = this.room_data_line.temperatureData[this.room_data_line.temperatureData.length - 1].temperatureValue;
    
    //console.log("Temp Push: " + this.room_data_line.temperatureData[this.room_data_line.temperatureData.length - 1].temperatureValue);
    //console.log("Temp Element: ")
    //console.log(this.room_data_line.temperatureData);
    //console.log(this.room_data_line.temperatureData[this.room_data_line.temperatureData.length - 1].temperatureValue);

    try{
    const temperatureValue = this.room_data_line.temperatureData[this.room_data_line.temperatureData.length - 1].temperatureValue;
    const currentTimestamp = (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
    const newDataPoint = { x: currentTimestamp, y: temperatureValue };
    
    if(this.chart.data.labels.last != this.currentTimestamp || temperatureValue != this.lastTemperatureValue/*this.room_data_line.temperatureData[this.room_data_line.temperatureData.length - 1].temperatureValue.toString()*/ )
    {
      this.lastTemperatureValue = temperatureValue;
      this.pushData(temperatureValue, currentTimestamp);
    }
    }
    catch{}
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

}

  

