import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { RoomsComponent } from './room/rooms/rooms.component';
import { DevicesComponent } from './devices/devices.component';
import {RoomListComponent} from "./room/room-list/room-list.component";
import {AddRoomComponent} from "./room/add-room/add-room.component";
import {RoomDetailsComponent} from "./room/room-details/room-details.component";
import {UpdateRoomComponent} from "./room/update-room/update-room.component";
import {RemoteDashboardComponent} from "./remote/remote-dashboard/remote-dashboard.component";
import {RemoteDetailsComponent} from "./remote-details/remote-details.component";


const routes: Routes = [
{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
{ path: 'dashboard', component: DashboardComponent },
{ path: 'rooms', component: RoomsComponent },
{ path: 'room-list', component: RoomListComponent },
{ path: 'room-details/:id', component: RoomDetailsComponent },
{ path: 'update-room/:id', component: UpdateRoomComponent },
{ path: 'remote-details/:id', component: RemoteDetailsComponent },
{ path: 'add-room', component: AddRoomComponent },
{ path: 'devices', component: DevicesComponent },
{ path: 'remote', component: RemoteDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
