

export type Door = {
    id: number;
    open: boolean;
  }

  export type Fenster = {
    id: number;
    open: boolean;
  }

  export type Light = {
    id: number;
    open: boolean;
  }

  export type Fan = {
    id: number;
    open: boolean;
  }

  export type TemperatureData = {
    id: number;
    temperatureValue: number;
    timestamp: Date;
  }

  export type PeopleData = {
    id: number;
    count: number;
    timestamp: Date;
  }

  export type Co2SensorData = {
    id: number;
    cO2value: number;
    timestamp: Date;
  }

  export type Room = {
    id: number;
    name: string;
    size: number;

    doors: Door[];
    roomWindows: Fenster[];
    lights: Light[];
    fans: Fan[];

    peopleData: PeopleData[];
    temperatureData: TemperatureData[];
    co2SensorData: Co2SensorData[];
  }





export const emptyRoom: Room = {
  id: 0,
  name: '',
  size: 0,
  doors: [],
  roomWindows: [],
  peopleData: [],
  lights: [],
  fans: [],
  temperatureData: [],
  co2SensorData: []
}

export const emptyDoor: Door = {
  id: 0,
  open: true
}

export const emptyWindow: Fenster = {
  id: 0,
  open: true
}

export const emptyLight: Light = {
  id: 0,
  open: true
}

export const emptyFan: Fan = {
  id: 0,
  open: true
}
