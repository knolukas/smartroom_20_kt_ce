import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "./entities/entity";

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

}
