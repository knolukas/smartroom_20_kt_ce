import { Component } from '@angular/core';
import {Room} from "../entities/entity";
import {RoomService} from "../room.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})


export class DashboardComponent {

  constructor(private roomService: RoomService) {
  }

  public roomNr: number = 0;
  public deviceNumber: number = 0;
  public roomArr: Room[] = [];


  
  /**
   * Load device number and get room data
   * @date 7/2/2023 - 11:57:12 PM
   */
  ngOnInit() {
    this.roomService.getRooms().subscribe((rooms) => {
      this.roomArr = rooms;
  
      this.deviceNumber = 0; // Reset the device number
  
      this.roomArr.forEach((room) => {
        this.deviceNumber +=
          room.roomWindows.length +
          room.lights.length +
          room.fans.length +
          room.doors.length;
      });
    });

    setInterval(() => {
      this.roomService.getRooms().subscribe((rooms) => {
        this.roomArr = rooms;
    
        this.deviceNumber = 0; // Reset the device number
    
        this.roomArr.forEach((room) => {
          this.deviceNumber +=
            room.roomWindows.length +
            room.lights.length +
            room.fans.length +
            room.doors.length;
        });
      });
  }, 1000);
}

}

