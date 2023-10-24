import { Component, OnInit, Input, SimpleChanges, Output, EventEmitter } from '@angular/core';
//import Chart from 'chart.js/auto';
import Chart, { ChartDataset } from 'chart.js/auto';
import {Room, Fan, Light, Fenster, Door} from "../entities/entity";
import { RoomDetailsComponent } from '../room/room-details/room-details.component';


interface DataPoint {
  x: string;
  y: number;
}

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})

export class BarChartComponent implements OnInit{
  //@Input() data:

 /*  data: Room = {
    id: 1,
    name: "test",
    size: 345,
    doors: [
      { id: 5, open: false },
      { id: 1, open: true },
      { id: 2, open: false },
      { id: 3, open: true },
      { id: 4, open: true },
      { id: 6, open: true }
    ],
    fans: [
      { id: 1, on: true },
      { id: 2, on: true },
      { id: 3, on: true },
      { id: 4, on: true },
      { id: 5, on: true },
      { id: 6, on: true },
      { id: 7, on: true }
    ],
    lights: [
      { id: 5, on: true },
      { id: 4, on: true },
      { id: 1, on: true },
      { id: 2, on: true },
      { id: 3, on: true },
      { id: 6, on: true },
      { id: 7, on: true },
      { id: 8, on: true },
      { id: 9, on: true }
    ],
    roomWindows: [
      { id: 1, open: true },
      { id: 2, open: true },
      { id: 3, open: true }
    ],
    temperaturData: [
      { timestamp: new Date('2023-05-13'), id: 4, cO2value: 0.7279176088704543 },
      { timestamp: new Date('2023-05-13'), id: 2, cO2value: 0.4714015434047626 },
      { timestamp: new Date('2023-05-13'), id: 1, cO2value: 0.8875773972712395 },
      { timestamp: new Date('2023-05-13'), id: 3, cO2value: 0.2600731460792005 },
      { timestamp: new Date('2023-05-13'), id: 5, cO2value: 0.4013382359328772 }
    ],
    co2SensorData: [
      { timestamp: new Date('2023-05-13'), id: 4, temperaturValue: 0.23486555138978604 },
      { timestamp: new Date('2023-05-13'), id: 2, temperaturValue: 0.16547511492408717 },
      { timestamp: new Date('2023-05-13'), id: 1, temperaturValue: 0.03832830914101948 },
      { timestamp: new Date('2023-05-13'), id: 3, temperaturValue: 0.34226200109755855 },
      { timestamp: new Date('2023-05-13'), id: 5, temperaturValue: 0.17730462355374066 }
    ]
  };*/

 //@Input()  data_test!: any;
 @Input() room_data!: Room;



data = this.room_data;

  public chart: any;
  public readonly yAxisMin = 0;
  public readonly yAxisMax = 1;
  public readonly yStep = 1;
  
  ngOnInit(): void {

    setTimeout(() => {
      if(this.chart){
        this.chart.destroy();
      }
      this.createChart();
      this.processData();
    }, 1000);

    console.log('OnInit - data- finally!:', this.room_data);

    try{
    this.chart.destroy();
    }
    catch(Error){}
    //light/fan/window/door.
    this.createChart();
    this.processData();
    this.updateChartData();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['room_data'] && changes['room_data'].currentValue) {
      //console.log('Room data changed:', changes['room_data'].currentValue);
      //this.updateChartData();
    }
  }



  
  /**
   * Delete old Chart and place new iteration
   * @date 7/2/2023 - 11:52:59 PM
   *
   * @public
   */
  public updateChartData(){
    if(!this.chart){
      this.createChart();
    }
    if(this.chart){
      this.chart.update();
      this.processData();
    }
   

  }


  public optionalUpdate(newData: Room): void {
      this.updateChartData();
/*
    if(this.chart){
      this.chart.destroy();
    }
    this.createChart();
    this.processData();
*/
    //console.log("UpateChart Data!");
   // console.log(this.chart.data.datasets[0].data);


    //if(this.chart.datasets[0].data !== undefined){}

      //console.log("prev is not undefined");

    /*  if(JSON.stringify(newData.doors) != JSON.stringify(this.previous_data?.doors)){
        console.log("WORKS!!!!!");
      }*/

     /* if(false){

      }

      else{
        if(!this.chart){
          //this.chart.destroy();
          this.createChart();
        }else{
          this.chart.destroy();
        }
        this.chart?.update();
        this.processData();
      }*/
  

    

   /* if(newData.doors != this.previous_data.co2SensorData){
      console.log("A Door opened!");
    }*/


  
    //this.chart.update();
  }
  


  constructor(){
  }

/*
  ngOnChanges(changes: { [propName: string]: SimpleChange }) {
    if (changes['data']) {
      const change = changes['data'];
      const currentValue = change.currentValue;
      const previousValue = change.previousValue;
      const isFirstChange = change.firstChange;

      //this.processData();

      console.log(`'data' property changed. Current value: ${currentValue}, Previous value: ${previousValue}. First change? ${isFirstChange}`);

    }
  }*/


  /**
   * Setup section with chart
   * @date 7/2/2023 - 11:53:27 PM
   */
  createChart(){

    this.chart = new Chart("MyBarChart", {
      type: 'bar', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: [],
	       datasets: [
          {
            label: "",
            data: [],
            backgroundColor: 'white',
            borderColor: "white",
            barThickness: 1
          },

        ]
      },
      options: {
        plugins: {
          title: {
            display: true,
            text: "Active Devices",
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
        elements: {

        },
        scales: {
          y: {
            min: 0,
            max: 1,
            ticks: {
              stepSize: 1,
              color: 'White',
              padding: 0,
              font: {
                family: "HelveticaNeueExtended",
                size: 12
              },
              callback: function(value: string | number) {
                if (typeof value === 'number') {
                  return value === 1 ? "On" : "Off";
                }
                return value;
              }
            
            },
            grid: {
              color: "rgba(255,255,255,0.15)"
            },
            suggestedMin: 0,
            suggestedMax: 1,
            position: 'left',
            type: 'linear',
            display: true,
            title: {
              display: true,
              text: '',
              color: 'White',
              font: {
                family: "HelveticaNeueExtended",
                size: 12,
                weight: 'bold'
              },
              padding: {
                top: 12,
                bottom: 12,


              }
            }
          },
          x: {
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
          },
        }
      }
    });
    //this.chart.canvas.parentNode.style.height = "500px";
      this.chart.canvas.parentNode.style.width = "800px";
      this.chart.update();
  }


  fanDataPoints: { label: string, value: boolean }[] = [];
  lightDataPoints: { label: string, value: boolean }[] = [];
  windowDataPoints: { label: string, value: boolean }[] = [];
  doorDataPoints: { label: string, value: boolean }[] = [];





  /**
   * Load Data into graph
   * @date 7/2/2023 - 11:54:36 PM
   *
   * @private
   */
  private processData() {

   // console.log("PROCESS DATA!");
   // console.log(this.room_data);

   if(this.room_data){

    /*console.log("Log");
    console.log(this.chart.data.datasets[0].data);
    console.log(this.chart.data.labels);*/
    /*console.log(this.room_data.doors);
    console.log(this.room_data.fans);
    console.log(this.room_data.lights);
    console.log(this.room_data.roomWindows);*/


    /*this.chart.data.datasets[0] = null;
*/
    // Process fan data points
    if (this.room_data.fans) {
    this.room_data.fans.forEach((fan: Fan) => {
      const existingDataPointIndex = this.fanDataPoints.findIndex(dataPoint => dataPoint.label === "Fan_" + fan.id);

      const labelToFind = "Fan_" + fan.id;
      var match = undefined;

      this.chart.data.labels.forEach((label: string, index: any) => {
        if (label === labelToFind) {
          match = index;
         /* console.log(label + " " + index);
          console.log(this.chart.data.datasets[0].data);*/
        }
      });

      if ( match !== undefined) {
        this.chart.data.datasets[0].data[match] = fan.open; 
      } else {
        this.chart.data.datasets[0].data.push([fan.open ? 1:0]);
        this.chart.data.labels.push("Fan_"+fan.id);
      }
    });
    }
    // Process light data points
    if (this.room_data.lights) {
    this.room_data.lights.forEach((light: Light) => {
      const existingDataPointIndex = this.lightDataPoints.findIndex(dataPoint => dataPoint.label === "Light_" + light.id);

      const labelToFind = "Light_" + light.id;
      var match = undefined;

      this.chart.data.labels.forEach((label: string, index: any) => {
        if (label === labelToFind) { match = index; }
      });

      if ( match !== undefined) {
        this.chart.data.datasets[0].data[match] = light.open; 
      } else {
        this.chart.data.datasets[0].data.push([light.open ? 1:0]);
        this.chart.data.labels.push("Light_"+light.id);
      }
    });
    }
    if (this.room_data.roomWindows) {
    this.room_data.roomWindows.forEach((window: Fenster) => {
      const existingDataPointIndex = this.doorDataPoints.findIndex(dataPoint => dataPoint.label === "Window_" + window.id);

      const labelToFind = "Window_" + window.id;
      var match = undefined;

      this.chart.data.labels.forEach((label: string, index: any) => {
        if (label === labelToFind) { match = index; }
      });

      if ( match !== undefined) {
        this.chart.data.datasets[0].data[match] = window.open; 
      } else {
        this.chart.data.datasets[0].data.push([window.open ? 1:0]);
        this.chart.data.labels.push("Window_"+window.id);
      }
    });
    }
    if (this.room_data.doors) {
    this.room_data.doors.forEach((door: Door) => {
      const existingDataPointIndex = this.doorDataPoints.findIndex(dataPoint => dataPoint.label === "Door_" + door.id);

      const labelToFind = "Door_" + door.id;
      var match = undefined;

      this.chart.data.labels.forEach((label: string, index: any) => {
        if (label === labelToFind) {
          match = index;
         /* console.log(label + " " + index);
          console.log(this.chart.data.datasets[0].data);*/
        }
      });

      if ( match !== undefined) {
        this.chart.data.datasets[0].data[match] = door.open; 
      } else {
        this.chart.data.datasets[0].data.push([door.open ? 1:0]);
        this.chart.data.labels.push("Door_"+door.id);
      }
    });
    }
  }
    // Update the graph with the new data points
    this.chart.update();
  }

  /*private getRandomTemperature() {

    return Math.floor(Math.random() * 8 + 15);
  }*/

}
