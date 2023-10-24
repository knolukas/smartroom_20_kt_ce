import {Component} from '@angular/core';
import {Room} from "../../entities/entity";
import {RoomService} from "../../room.service";

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent {
  public rooms: Room[] = [];

  constructor(private roomService: RoomService) {
  }


  // Init Room
  ngOnInit() {
    this.roomService.getRooms().subscribe((rooms) => {
      this.rooms = rooms;
      console.log(this.rooms);
    })
  }
}

