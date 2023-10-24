import { Component } from '@angular/core';
import {
  Door,
  emptyDoor,
  emptyFan,
  emptyLight,
  emptyRoom,
  emptyWindow,
  Fan,
  Light,
  Room,
  Fenster
} from "../../entities/entity";
import { ActivatedRoute, Router } from "@angular/router";
import { RoomService } from "../../room.service";
import { timeout } from 'rxjs';

@Component({
  selector: 'app-update-room',
  templateUrl: './update-room.component.html',
  styleUrls: ['./update-room.component.scss']
})
export class UpdateRoomComponent {

  public id: number = 0;
  public room: Room = emptyRoom;

  public door: Door = emptyDoor;
  public window: Fenster = emptyWindow;

  public light: Light = emptyLight;
  public fan: Fan = emptyFan;

  public nextFanId: number = 0;
  public nextLightId: number = 0;
  public nextDoorId: number = 0;
  public nextWindowId: number = 0;
  isGettingNextID: boolean = false;

  constructor(private route: ActivatedRoute, private roomService: RoomService, private router: Router) {}

  ngOnInit() {
    // Load Room by ID
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.roomService.getRoom(this.id).subscribe((room) => {
        this.room = room;
        console.log("GET");
        console.log(this.room);
      });
    });

  }

  private delayedUpdate(){


    this.updateRoom();
  }

  public updateRoom() {
    // Update the room
    //console.log(this.room);
    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.router.navigate(['room-details/', this.room.id]);
    });
  }

  addDoor(id: number) {
    // Add a door to the room
    this.room.doors.push({
      id: id,
      open: true
    });

    this.delayedUpdate();
    /*
    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.router.navigate(['room-details/' + this.room.id]);
    });
    this.updateRoom();*/
  }

  addWindow(id: number) {
    // Add a window to the room
    this.room.roomWindows.push({
      id: id,
      open: true
    });

    this.delayedUpdate();
    /*

    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.router.navigate(['room-details/' + this.room.id]);
    });
    this.updateRoom();*/
  }

  addLight(id: number) {
    // Add a light to the room
    this.room.lights.push({
      id: id,
      open: true
    });

    this.delayedUpdate();
/*
    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.router.navigate(['room-details/' + this.room.id]);
    });
    this.updateRoom();*/
  }

  addFan(id: number) {
    // Add a fan to the room
    this.room.fans.push({
      id: id,
      open: true
    });
    
    this.delayedUpdate();
    /*
    this.roomService.updateRoom(this.room).subscribe((data) => {
      this.router.navigate(['room-details/' + this.room.id]);
    });
    this.updateRoom();*/
  }


  public async getNextID(Selection: number) {
    if (this.isGettingNextID) {
      setTimeout(() => {
        this.router.navigate(['room-details/', this.id]);
        return;
      }, 5000);

      return;
    }
  
    this.isGettingNextID = true;
    try {
      const data = await this.roomService.getRooms().toPromise();
  
      const doors: number[] = [];
      const roomWindows: number[] = [];
      const lights: number[] = [];
      const fans: number[] = [];
  
      data?.forEach((room) => {
        doors.push(...room.doors.map((door) => door.id));
        roomWindows.push(...room.roomWindows.map((window) => window.id));
        lights.push(...room.lights.map((light) => light.id));
        fans.push(...room.fans.map((fan) => fan.id));
      });
  
      this.nextFanId = Math.max(...fans) + 1;
      this.nextLightId = Math.max(...lights) + 1;
      this.nextDoorId = Math.max(...doors) + 1;
      this.nextWindowId = Math.max(...roomWindows) + 1;
  
      // Ensure IDs are not negative
      this.nextFanId = Math.max(this.nextFanId, 0);
      this.nextLightId = Math.max(this.nextLightId, 0);
      this.nextDoorId = Math.max(this.nextDoorId, 0);
      this.nextWindowId = Math.max(this.nextWindowId, 0);
  
      await new Promise((resolve) => setTimeout(resolve, 2000));
  
      this.route.params.subscribe(params => {
        this.id = +params['id'];
        this.roomService.getRoom(this.id).subscribe((room) => {
          this.room = room;
        });
      });
  
      switch (Selection) {
        case 1:
          this.addLight(this.nextLightId);
          break;
        case 2:
          this.addFan(this.nextFanId);
          break;
        case 3:
          this.addDoor(this.nextDoorId);
          break;
        case 4:
          this.addWindow(this.nextWindowId);
          break;
        default:
          break;
      }
    } catch (error) {
      console.error("Error:", error);
    }
  }
  

  public addLightButton() {
    // Add a light to the room and update the room
    this.getNextID(1);
    /*this.room.lights.push({
      on: false,
      id: this.nextLightId
    });
    console.log("ID: " + this.nextLightId); 
    this.updateRoom();*/
  }


  public addFanButton() {
    // Add a fan to the room and update the room
    this.getNextID(2);
    /*this.room.fans.push({
      on: false,
      id: this.nextFanId,
    });
    console.log("ID: " + this.nextFanId); 
    this.updateRoom();*/
  }

  public addDoorButton() {
    // Add a door to the room and update the room
    this.getNextID(3);
    /*this.room.doors.push({
      open: false,
      id: this.nextDoorId
    });
    console.log("ID: " + this.nextDoorId); 
    this.updateRoom();*/
  }

  public addWindowButton() {
    // Add a window to the room and update the room
    this.getNextID(4);
    /*this.room.roomWindows.push({
      id: this.nextWindowId,
      open: false
    });
    console.log("ID: " + this.nextWindowId); 
    this.updateRoom();*/
  }


}
