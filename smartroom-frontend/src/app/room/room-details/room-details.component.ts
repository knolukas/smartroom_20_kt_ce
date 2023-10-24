import { Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoomService } from '../../room.service';
import { Fan, Room } from '../../entities/entity';
import { BarChartComponent } from 'src/app/bar-chart/bar-chart.component';
import { LineChartComponent } from 'src/app/line-chart/line-chart.component';
import { Co2LineChartComponent } from 'src/app/line-chart-co2/line-chart-co2.component';
import { RoomListComponent } from '../room-list/room-list.component';
import { retryWhen, delay, take } from 'rxjs/operators';

var emptyRoom: Room = {
  id: 100000,
  name: '_',
  size: 0,
  doors: [],
  roomWindows: [],
  peopleData: [],
  lights: [],
  fans: [],
  temperatureData: [],
  co2SensorData: []
};

@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.scss']
})
export class RoomDetailsComponent implements OnInit {
  @ViewChild(BarChartComponent) barChartComponent!: BarChartComponent;
  @ViewChild(LineChartComponent) lineChartComponent!: LineChartComponent;
  @ViewChild(Co2LineChartComponent) co2lineChartComponent!: Co2LineChartComponent;

  public id: number = 0;
  public room!: Room;
  public lastPeopleNumber = "0";
  public lastPeopleId = "0"

  constructor(
    private route: ActivatedRoute,
    private roomService: RoomService,
    private router: Router,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    // Fetch room details from the server
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.roomService.getRoom(this.id).subscribe((room) => {
        this.room = room;
      });
    });

    /* Updates room information - necessary for charts */
    setInterval(() => {
      this.updateRoomLoop();

      //let root = <HTMLElement>document.getElementById("PeopleNumber");
      const root = <HTMLElement>document.getElementById("PeopleNumber");
      var peopleNum = this.room.peopleData[this.room.peopleData.length - 1].count.toString();
      var peopleNumId = this.room.id;
      var activeId = "";

      if(peopleNum == null) {
        peopleNum = "...";
      }

      const currentUrl = window.location.href;
      const urlSegments = currentUrl.split('/');
      const lastSegment = urlSegments[urlSegments.length - 1];
      
      const number = parseInt(lastSegment, 10);
      if (!isNaN(number)) {
        activeId = String(number);
      }

      if(peopleNum == this.lastPeopleNumber && this.lastPeopleNumber != "Load..." && ((Number(peopleNum) - 2) <= Number(this.lastPeopleNumber)) && ((Number(peopleNum) + 2) >= Number(this.lastPeopleNumber)) && Number(this.room.id) == Number(activeId)){
        this.renderer.setProperty(root, 'textContent', peopleNum)
        console.log("HMTL Elemenet");
        console.log(root);
      }else{
        this.lastPeopleNumber = peopleNum;
        this.lastPeopleId = String(peopleNumId);
      }
 


    }, 2000);
  }

  public deleteRoom() {
   /* console.log("Log ID: ");
    console.log(this.id);*/

    // Create an instance of RoomListComponent to access its methods
    const roomList = new RoomListComponent(this.roomService, this.router);
    roomList.deleteRoom(this.room);
    roomList.loadRooms();

    /* this.roomService.removeRoom(this.id).subscribe((data) => {
      this.router.navigate(['room-list']);
    }); */
  }

  public update() {
    // Navigate to the update room page 
    //this works!

    this.updateRoom();
    this.router.navigate(['update-room/', this.id]);
  }

  public addValues() {
    // Add values to the room
    this.roomService.addValues(this.id).subscribe((room) => {
      this.router.navigate(['dashboard']);
    });
  }

  updateDoorState(door: any) {
    console.log('Door state changed:', this.room.doors);
    this.updateRoom();
    
   // this.roomService.updateRoom(this.room);
  }

  updateFanState(fan: any) {
    console.log('Fan state changed:', fan.open);
    this.updateRoom();
    
   // this.roomService.updateRoom(this.room);
  }

  updateLightState(light: any) {
    console.log('Light state changed:', light.open);
    this.updateRoom();
    
    //this.roomService.updateRoom(this.room);
  }

  updateWindowState(window: any) {
    console.log('Window state changed:', window.open);
    this.updateRoom();
   // this.roomService.updateRoom(this.room);
  }

  public removeFanFromRoom(device: Fan) {
    const index = this.room.fans.findIndex(d => d.id === device.id);
    if (index !== -1) {
      // Remove the fan from the room's fan list
      this.room.fans.splice(index, 1);
      this.roomService.updateRoom(this.room).subscribe((data) => {
        this.router.navigate(['room-details/' + this.room.id]);
      });
      console.log(this.room);
      this.updateRoom();
    }
  }



  public updateRoom() {
    // Update the room data on the server
    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.room = data;
     // console.log(data);
    });

    // Update the charts
    //this.barChartComponent.updateChartData();
    this.lineChartComponent.updateChart();
    this.co2lineChartComponent.updateChart();
   /* this.roomService.updateRoom(this.room)
    .pipe(
      retryWhen(errors => errors.pipe(delay(1000), take(3))) // Retry up to 3 times with a 1-second delay
    )
    .subscribe(
      (data) => {
        this.room = data;
        // Update the charts
        // this.barChartComponent.updateChartData();
        this.lineChartComponent.updateChart();
        this.co2lineChartComponent.updateChart();
      },
      (error) => {
        console.error("Update failed:", error);
      }
    );*/
  }

  public updateRoomLoop() {
    // Continuously update the room data on the server
    //console.log("Check Data: ");
    console.log(this.room);

    //Update does work some of the time here

    this.roomService.updateRoom(this.room).subscribe((data) => {

      this.room = data;
      this.barChartComponent.optionalUpdate(data);
    });

    // Update the charts
    this.lineChartComponent.updateChart();
    this.co2lineChartComponent.updateChart();
    //this.barChartComponent.updateChartData();
 
  
  }
}
