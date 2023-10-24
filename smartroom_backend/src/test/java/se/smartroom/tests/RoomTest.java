package se.smartroom.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.smartroom.entities.Room;
import se.smartroom.entities.data.Co2SensorData;
import se.smartroom.entities.data.TemperatureData;
import se.smartroom.entities.people.PeopleData;
import se.smartroom.entities.physicalDevice.Door;
import se.smartroom.entities.physicalDevice.Fenster;
import se.smartroom.entities.physicalDevice.Fan;
import se.smartroom.entities.physicalDevice.Light;
import se.smartroom.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RoomTest {
//Arrange = Creation of instances

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        String expectedName = "Room 1";
        int expectedSize = 100;
        Door door = new Door();
        Fenster fenster = new Fenster();
        Light light = new Light();
        Fan fan = new Fan();
        Co2SensorData co2SensorData = new Co2SensorData();
        TemperatureData temperatureData = new TemperatureData();
        PeopleData peopleData = new PeopleData();

        // Act
        Room room = new Room(expectedName, expectedSize,
                Collections.singletonList(door),
                Collections.singletonList(fenster),
                Collections.singletonList(light),
                Collections.singletonList(fan),
                Collections.singletonList(co2SensorData),
                Collections.singletonList(temperatureData),
                Collections.singletonList(peopleData)
        );

        // Assert
        assertEquals(expectedName, room.getName());
        assertEquals(expectedSize, room.getSize());
        assertEquals(Collections.singletonList(door), room.getDoors());
        assertEquals(Collections.singletonList(fenster), room.getRoomWindows());
        assertEquals(Collections.singletonList(light), room.getLights());
        assertEquals(Collections.singletonList(fan), room.getFans());
        assertEquals(Collections.singletonList(co2SensorData), room.getCo2SensorData());
        assertEquals(Collections.singletonList(temperatureData), room.getTemperatureData());
        assertEquals(Collections.singletonList(peopleData), room.getPeopleData());
    }

    @Test
    public void testConstructorWithoutParameters() {
        // Arrange
        String expectedName = "Room 1";
        int expectedSize = 100;

        // Act
        Room room = new Room(expectedName, expectedSize);

        // Assert
        assertEquals(expectedName, room.getName());
        assertEquals(expectedSize, room.getSize());
        assertEquals(Collections.emptyList(), room.getDoors());
        assertEquals(Collections.emptyList(), room.getRoomWindows());
        assertEquals(Collections.emptyList(), room.getLights());
        assertEquals(Collections.emptyList(), room.getFans());
        assertEquals(Collections.emptyList(), room.getCo2SensorData());
        assertEquals(Collections.emptyList(), room.getTemperatureData());
        assertEquals(Collections.emptyList(), room.getPeopleData());
    }
    @Test
    public void testCo2SensorDataMethods() {
        // instance of DataContainer
        Room dataContainer = new Room(RoomRepository.class);

        // sample lists of Co2SensorData objects
        List<Co2SensorData> expectedCo2SensorData = new ArrayList<>();
        expectedCo2SensorData.add(new Co2SensorData(1.23));
        expectedCo2SensorData.add(new Co2SensorData(4.56));

        // Set the sample lists using setCo2SensorData()
        dataContainer.setCo2SensorData(expectedCo2SensorData);

        // Retrieve the lists using getCo2SensorData()
        List<Co2SensorData> actualCo2SensorData = dataContainer.getCo2SensorData();

        // Assert that the retrieved lists match the expected lists
        assertEquals(expectedCo2SensorData, actualCo2SensorData);
    }

    @Test
    public void testTemperatureDataMethods() {
        // instance of DataContainer
        Room dataContainer = new Room(RoomRepository.class);

        // sample lists of TemperatureData objects
        List<TemperatureData> expectedTemperatureData = new ArrayList<>();
        expectedTemperatureData.add(new TemperatureData(12.34));
        expectedTemperatureData.add(new TemperatureData(56.78));

        // Set the sample lists using setTemperatureData()
        dataContainer.setTemperatureData(expectedTemperatureData);

        // Retrieve the lists using getTemperatureData()
        List<TemperatureData> actualTemperatureData = dataContainer.getTemperatureData();

        // Assert that the retrieved lists match the expected lists
        assertEquals(expectedTemperatureData, actualTemperatureData);
    }

    @Test
    public void testToString() {
        // instance of Room
        Room room = new Room(RoomRepository.class);
        room.setId(1);
        room.setName("Living Room");
        room.setSize(20);

        List<Door> doors = new ArrayList<>();
        doors.add(new Door(true));
        List<Fenster> windows = new ArrayList<>();
        windows.add(new Fenster(true));
        List<Light> lights = new ArrayList<>();
        lights.add(new Light(true));
        List<Fan> fans = new ArrayList<>();
        fans.add(new Fan(true));
        List<Co2SensorData> co2SensorData = new ArrayList<>();
        co2SensorData.add(new Co2SensorData(1.23));
        co2SensorData.add(new Co2SensorData(4.56));

        List<TemperatureData> temperatureData = new ArrayList<>();
        temperatureData.add(new TemperatureData(12.34));
        temperatureData.add(new TemperatureData(56.78));

        List<PeopleData> peopleData = new ArrayList<>();

        room.setDoors(doors);
        room.setRoomWindows(windows);
        room.setFans(fans);
        room.setLights(lights);
        room.setCo2SensorData(co2SensorData);
        room.setTemperatureData(temperatureData);
        room.setPeopleData(peopleData);

        // Invoke the toString() method
        String result = room.toString();

        // Define the expected string representation
        String expectedString = "Room{" +
                "id=1, " +
                "name='Living Room', " +
                "size=20, " +
                "doors="+ doors.toString()+", " +
                "roomWindows=" + windows.toString()+", " +
                "lights=" + lights.toString()+", " +
                "fans=" + fans.toString()+", " +
                "co2SensorData=" + co2SensorData.toString() + ", " +
                "temperatureData=" + temperatureData.toString() + ", " +
                "peopleData=" + "[]" +
                "}";

        // Assert that the returned string matches the expected string
        assertEquals(expectedString, result);

        //hash methode testing
        int expectedHashCode = Objects.hash(room.getId(), room.getName(), room.getSize(), room.getDoors(),
                room.getRoomWindows(), room.getLights(), room.getFans(), room.getCo2SensorData(), room.getTemperatureData(), room.getPeopleData());

        // Invoke the hashCode() method
        int result2 = room.hashCode();

        assertEquals(expectedHashCode,result2);
    }

    @Test
    public void testEquals() {
        // Arrange
        Room room1 = new Room("Room 1", 100,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );

        Room room2 = new Room("Room 1", 100,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );

        Room room3 = new Room("Room 2", 200,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );

        // Act & Assert
        assertEquals(room1, room2); // The two instances should be considered equal
        assertEquals(room2, room1); // The order of comparison should not matter
        assertNotEquals(room1, room3); // The two instances should not be considered equal
        assertNotEquals(room3, room1); // The order of comparison should not matter
    }
}
