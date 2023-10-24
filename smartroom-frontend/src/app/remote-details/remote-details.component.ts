import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RoomService} from "../room.service";
import {emptyRoom, Room} from "../entities/entity";
import { interval } from 'rxjs';

@Component({
  selector: 'app-remote-details',
  templateUrl: './remote-details.component.html',
  styleUrls: ['./remote-details.component.scss']
})
export class RemoteDetailsComponent {

  public id: number = 0;
  public room: Room = emptyRoom;

  constructor(private route: ActivatedRoute, private roomService: RoomService, private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.roomService.getRoom(this.id).subscribe((room) => {
        this.room = room;
      });
    });

  }

  public updateRoom() {
    this.roomService.updateRoom(this.room).subscribe((data) => {
      console.log("TEST?");
      this.room = data;
    });
  }

}
