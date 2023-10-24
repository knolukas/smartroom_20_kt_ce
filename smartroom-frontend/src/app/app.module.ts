import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RoomsComponent } from './room/rooms/rooms.component';
import { DevicesComponent } from './devices/devices.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { RoomService } from './room.service';
import { FormsModule } from '@angular/forms';
import { RoomListComponent } from './room/room-list/room-list.component';
import { AddRoomComponent } from './room/add-room/add-room.component';
import { RoomDetailsComponent } from './room/room-details/room-details.component';
import { UpdateRoomComponent } from './room/update-room/update-room.component';
import { RemoteDashboardComponent } from './remote/remote-dashboard/remote-dashboard.component';
import { RemoteDetailsComponent } from './remote-details/remote-details.component';
import { LineChartComponent } from './line-chart/line-chart.component';
import { AddValuesComponent } from './room/add-values/add-values.component';
import { Co2LineChartComponent } from './line-chart-co2/line-chart-co2.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    RoomsComponent,
    DevicesComponent,
    RoomListComponent,
    AddRoomComponent,
    RoomDetailsComponent,
    UpdateRoomComponent,
    RemoteDashboardComponent,
    RemoteDetailsComponent,
    LineChartComponent,
    Co2LineChartComponent,
    AddValuesComponent,
    BarChartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    HttpClientModule,
    RoomService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
