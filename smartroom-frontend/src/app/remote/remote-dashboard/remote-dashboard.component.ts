import { Component } from '@angular/core';
import {Room} from "../../entities/entity";
import {RoomService} from "../../room.service";

@Component({
  selector: 'app-remote-dashboard',
  templateUrl: './remote-dashboard.component.html',
  styleUrls: ['./remote-dashboard.component.scss']
})
export class RemoteDashboardComponent {

  public rooms: Room[] = [];

  constructor(private roomService: RoomService) {
  }

  ngOnInit() {
    this.roomService.getRooms().subscribe((rooms) => {
      this.rooms = rooms;
      //console.log(this.rooms);
    })
  }

}
