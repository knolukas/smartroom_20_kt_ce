import { Component, OnInit } from '@angular/core';
//import Chart from 'chart.js/auto';
import Chart, { ChartDataset } from 'chart.js/auto';

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
              padding: 20,
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
              callback: function(value, index, values) {
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

  ngOnInit(): void {

    var curTime : Date = new Date();
    for(var i:number = 15; i > 0; i = i - 1){
      const randomTemperature = this.getRandomTemperature();
      const currentTimestamp = (new Date(curTime.getTime()- (1000 * i)).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
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
  
    return Math.floor(Math.random() * 8 + 15);
  }

}

/*
   private updateChart() {
    const randomTemperature = this.getRandomTemperature();
    const currentTimestamp = (new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })).toString();
    const newDataPoint = { x: currentTimestamp, y: randomTemperature };
    
    this.temperatureData.push(newDataPoint);
  
    if (this.temperatureData.length > 8) {
      this.temperatureData.shift();
    }

    console.log(this.temperatureData);
    
    this.chart.data.datasets[0].data = this.temperatureData;
    this.chart.update({duration: 200, easing: 'linear'});}*/
  /*createChart(){
    
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
    
    const weight: number[] = [60.0, 60.2, 59.1, 61.4, 59.9, 60.2, 59.8, 58.6, 59.6, 59.2];
    
    const labels: string[] = [
      "Week 1",
      "Week 2",
      "Week 3",
      "Week 4",
      "Week 5",
      "Week 6",
      "Week 7",
      "Week 8",
      "Week 9",
      "Week 10"
    ];

    //const canvas = document.getElementById("canvas") as HTMLCanvasElement;
    //const canvas = document.getElementById('MyChart') as HTMLCanvasElement;
    //const ctx: CanvasRenderingContext2D = (document.getElementById("MyChart") as HTMLCanvasElement).getContext("2d")!;
    //const ctx: CanvasRenderingContext2D | null = document.getElementById('myChart')?.getContext('2d');
    
    const canvas = document.getElementById("MyChart") as HTMLCanvasElement;
    const ctx = canvas.getContext("2d") as CanvasRenderingContext2D;
 
    const gradient: CanvasGradient = ctx.createLinearGradient(0, 25, 0, 300);
    gradient.addColorStop(0, colors.purple.half);
    gradient.addColorStop(0.35, colors.purple.quarter);
    gradient.addColorStop(1, colors.purple.zero);
    
       const options: Chart.ChartConfiguration = {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              fill: true,
              backgroundColor: gradient,
              pointBackgroundColor: colors.purple.default,
              borderColor: colors.purple.default,
              data: weight,
              lineTension: 0.2,
              borderWidth: 2,
              pointRadius: 3
            }
          ]
        },
        options: {
          layout: {
            padding: 10
          },
          responsive: true,
          legend: {
            display: false
          },
      
          scales: {
            xAxes: [
              {
                gridLines: {
                  display: false
                },
                ticks: {
                  padding: 10,
                  autoSkip: false,
                  maxRotation: 15,
                  minRotation: 15
                }
              }
            ],
            yAxes: [
              {
                scaleLabel: {
                  display: true,
                  labelString: "Weight in KG",
                  padding: 10
                },
                gridLines: {
                  display: true,
                  color: colors.indigo.quarter
                },
                ticks: {
                  beginAtZero: false,
                  max: 63,
                  min: 57,
                  padding: 10
                }
              }
            ]
          }
        }
      };
  }

      /*
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
    
    const weight = [60.0, 60.2, 59.1, 61.4, 59.9, 60.2, 59.8, 58.6, 59.6, 59.2];
    
    const labels = [
      "Week 1",
      "Week 2",
      "Week 3",
      "Week 4",
      "Week 5",
      "Week 6",
      "Week 7",
      "Week 8",
      "Week 9",
      "Week 10"
    ];
    
    const canvas = document.getElementById("MyChart") as HTMLCanvasElement | null;
    if (canvas) {
    //const ctx = canvas.getContext("2d");
    
    const canvas = document.getElementById("MyChart") as HTMLCanvasElement;
    const ctx = canvas.getContext("2d");

    if(ctx){
    ctx.canvas.height = 100;
    
    //gradient = ctx.createLinearGradient(0, 25, 0, 300);
    //gradient.addColorStop(0, colors.purple.half);
    //gradient.addColorStop(0.35, colors.purple.quarter);
    //gradient.addColorStop(1, colors.purple.zero);
    
    const options = {
      type: "line",
      data: {
        labels: labels,
        datasets: [
          {
            fill: true,
            //backgroundColor: gradient,
            pointBackgroundColor: colors.purple.default,
            borderColor: colors.purple.default,
            data: weight,
            lineTension: 0.2,
            borderWidth: 2,
            pointRadius: 3
          }
        ]
      },
      options: {
        layout: {
          padding: 10
        },
        responsive: true,
        legend: {
          display: false
        },
    
        scales: {
          xAxes: [
            {
              gridLines: {
                display: false
              },
              ticks: {
                padding: 10,
                autoSkip: false,
                maxRotation: 15,
                minRotation: 15
              }
            }
          ],
          yAxes: [
            {
              scaleLabel: {
                display: true,
                labelString: "Weight in KG",
                padding: 10
              },
              gridLines: {
                display: true,
                color: colors.indigo.quarter
              },
              ticks: {
                beginAtZero: false,
                max: 63,
                min: 57,
                padding: 10
              }
            }
          ]
        }
      }
    };
    
    window.onload = function () {
      window.myLine = new Chart(ctx, options);
      //Chart.defaults.global.defaultFontColor = colors.indigo.default;
      //Chart.defaults.global.defaultFontFamily = "Fira Sans";
    };
    }
   }
  }*/


   /*
   const options: Chart.ChartConfiguration = {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              fill: true,
              backgroundColor: gradient,
              pointBackgroundColor: colors.purple.default,
              borderColor: colors.purple.default,
              data: weight,
              lineTension: 0.2,
              borderWidth: 2,
              pointRadius: 3
            }
          ]
        },
        options: {
          layout: {
            padding: 10
          },
          responsive: true,
          legend: {
            display: false
          },
      
          scales: {
            xAxes: [
              {
                gridLines: {
                  display: false
                },
                ticks: {
                  padding: 10,
                  autoSkip: false,
                  maxRotation: 15,
                  minRotation: 15
                }
              }
            ],
            yAxes: [
              {
                scaleLabel: {
                  display: true,
                  labelString: "Weight in KG",
                  padding: 10
                },
                gridLines: {
                  display: true,
                  color: colors.indigo.quarter
                },
                ticks: {
                  beginAtZero: false,
                  max: 63,
                  min: 57,
                  padding: 10
                }
              }
            ]
          }
        }
    };*/
  //}

  /*,
          {
            label: "Profit",
            data: [
              { x: '2022-05-10', y: 542 },
              { x: '2022-05-11', y: 542 },
              { x: '2022-05-12', y: 536 },
              { x: '2022-05-13', y: 327 },
              { x: '2022-05-14', y: 17 },
              { x: '2022-05-15', y: 0 },
              { x: '2022-05-16', y: 538 },
              { x: '2022-05-17', y: 541 },
            ],
            backgroundColor: 'limegreen'
          }  */

   /*,
      options: {
        scales: {
          xAxes: [{
            type: 'time',
            time: {
              unit: 'day'
            }
          }],
          yAxes: [{
            ticks: {
              beginAtZero: true
            }
          }]
        }
      }*/
  
  

