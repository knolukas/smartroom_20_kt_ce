import { Component, OnInit } from '@angular/core';
import { Room } from '../../entities/entity';
import { RoomService } from '../../room.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.scss']
})
export class RoomListComponent implements OnInit {

  public rooms: Room[] = [];

  constructor(private roomService: RoomService, private router: Router) {}

  ngOnInit() {
    // Load rooms on component initialization
    this.loadRooms();
  }

  public loadRooms() {
    // Get rooms from the service
    this.roomService.getRooms().subscribe((rooms) => {
      if (this.areRoomsDifferent(this.rooms, rooms)) {
        // Update rooms if there are differences
        this.rooms = rooms;
        // Perform additional actions here
        console.log('Rooms have been updated:', this.rooms);
      }
    });
  }

  public forceLoadRooms() {
    console.log("FORCE!");

    // Forcefully load rooms from the service
    this.roomService.getRooms().subscribe((rooms) => {
      this.rooms = rooms;
      // Perform additional actions here
      console.log('Rooms have been updated:', this.rooms);
    });
  }

  public updateRoomNameSize(room: Room, newName: string, newSize: number) {
    // Update the name and size of the room
    room.name = newName;
    room.size = newSize;

    // Send the updated room to the service

    //this.roomService.updateRoom(room).subscribe((data) => {});


    //console.log(room);


    const interval = setInterval(() => {
      this.roomService.getRoom(room.id).subscribe((loadedRoom) => {
        if (loadedRoom.name === newName && loadedRoom.size === newSize) {
          clearInterval(interval); 
        } else {
          loadedRoom.name = newName;
          loadedRoom.size = newSize;
          this.roomService.updateRoom(loadedRoom).subscribe((data) => {
            console.log('Room updated:', data);
          });
        }
      });
    }, 1000);

  }

  public deleteAllRoom(Room: Room) {
    // Delete all rooms
    this.roomService.getRooms().subscribe((rooms) => {
      if (this.areRoomsDifferent(this.rooms, rooms)) {
        this.rooms = rooms;
      }
    });
    this.rooms.forEach(element => {
      this.roomService.removeRoom(element.id).subscribe((data) => {});
    });
  }

  public deleteRoom(Room: Room) {
    console.log("Log ID: ");
    console.log(Room.id);

    // Delete a specific room
    this.roomService.removeRoom(Room.id).subscribe((data) => {
      this.loadRooms();
      this.forceLoadRooms(); // Move the forceLoadRooms call here
      this.router.navigate(['room-list']);
    });
 /* }
    this.roomService.removeRoom(Room.id).subscribe((data) => {
      this.router.navigate(['room-list']);
    });
    this.loadRooms();
    */
  }

  private areRoomsDifferent(oldRooms: Room[], newRooms: Room[]): boolean {
    // Compare the length of the arrays
    if (oldRooms.length !== newRooms.length) {
      return true;
    }

    // Compare each room in the arrays
    for (let i = 0; i < oldRooms.length; i++) {
      if (oldRooms[i].id !== newRooms[i].id || oldRooms[i].name !== newRooms[i].name) {
        return true;
      }
    }

    return false;
  }

  public editRoomVisible: boolean = false;
  public detailsTabVisible: boolean = false;
  public mainTabVisible: boolean = true;

  // Navigate the room tab
  public goToMainTab(Site: String) {
    if (Site == 'editRoom') {
      this.editRoomVisible = true;
      this.detailsTabVisible = false;
      this.mainTabVisible = false;
    }
    if (Site == 'detailsTab') {
      this.editRoomVisible = false;
      this.detailsTabVisible = true;
      this.mainTabVisible = false;
    }
    if (Site == 'mainTab') {
      this.editRoomVisible = false;
      this.detailsTabVisible = false;
      this.mainTabVisible = true;
    }
  }

}
