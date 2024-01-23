import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Light, Room} from "./entities/entity";

@Injectable({
  providedIn: 'root'
})

export class RoomService {

  private readonly baseurl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {
  }

  public getRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(this.baseurl + 'rooms');
  }

  public addRoom(room: Room): Observable<Room> {
    return this.http.post<Room>(this.baseurl + 'room', room);
  }

  public getRoom(id: number): Observable<Room> {
    return this.http.get<Room>(this.baseurl + 'room/' + id);
  }

  public removeRoom(id: number): Observable<Room> {
    return this.http.delete<Room>(this.baseurl + 'room/' + id);
  }

  public updateRoom(room: Room): Observable<Room> {
    return this.http.put<Room>(this.baseurl + 'room', room);
  }

  public addValues(id: number): Observable<Room> {
    return this.http.put<Room>(this.baseurl + 'room/' + id + '/addValues', null);
  }

  public turnOnLights(roomId: number, light: Light): Observable<Light> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const url = this.baseurl + 'room/' + roomId + '/lights/on';

    console.log("Enter turnOnLights");
    console.log("Request URL:", url);
    console.log("Request Payload:", light);

    return this.http.post<Light>(url, light, { headers: headers });
  }

  public turnOffLights(roomId: number, light: Light): Observable<Light> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const url = this.baseurl + 'room/' + roomId + '/lights/off';

    console.log("Enter turnOnLights");
    console.log("Request URL:", url);
    console.log("Request Payload:", light);

    return this.http.post<Light>(url, light, { headers: headers });
  }

  public updateColor(roomId: number, light: Light, color: string): Observable<Light> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const url = this.baseurl + 'room/' + roomId + '/lights/update_color?colorId=' + color;

    console.log("Enter updateColor");
    console.log("Request URL:", url);
    console.log("Request Payload:", light);

    return this.http.post<Light>(url, light, { headers: headers });
  }

}
