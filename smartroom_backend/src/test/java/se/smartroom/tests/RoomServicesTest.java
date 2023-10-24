package se.smartroom.tests;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.smartroom.entities.Room;
import se.smartroom.entities.data.Co2SensorData;
import se.smartroom.entities.data.TemperatureData;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.entities.people.PeopleData;
import se.smartroom.repositories.EnvironmentDataRepository;
import se.smartroom.repositories.RoomRepository;
import se.smartroom.services.RoomService;
import se.smartroom.entities.physicalDevice.Door;
import se.smartroom.entities.physicalDevice.Fenster;
import se.smartroom.entities.physicalDevice.Fan;
import se.smartroom.entities.physicalDevice.Light;


import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static se.smartroom.entities.environment.SEASONSTATUS.SUMMER;

@ExtendWith(MockitoExtension.class)
public class RoomServicesTest {
    //Arrange = Creation of instances
    @Mock
    private RoomRepository repository;

    @InjectMocks
    private RoomService roomService;

    @Mock
    private EnvironmentDataRepository environmentDataRepository;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Test
    public void testUpdateRoom() {
        // Arrange
        RoomRepository mockRoomRepository = mock(RoomRepository.class);
        RoomService roomService = new RoomService(mockRoomRepository);

        Room existingRoom = new Room("Existing Room", 1);
        Room updatedRoom = new Room("Updated Room", 1);

        // Set up the behavior of the mock repository
        when(mockRoomRepository.findById(existingRoom.getId())).thenReturn(Optional.of(existingRoom));
        when(mockRoomRepository.save(existingRoom)).thenReturn(existingRoom);

        // Act
        Room result = roomService.updateRoom(updatedRoom);

        // Assert
        assertEquals(existingRoom, result);
        assertEquals(updatedRoom.getName(), existingRoom.getName());
        assertEquals(updatedRoom.getSize(), existingRoom.getSize());
        assertEquals(updatedRoom.getDoors(), existingRoom.getDoors());
        assertEquals(updatedRoom.getRoomWindows(), existingRoom.getRoomWindows());
        assertEquals(updatedRoom.getLights(), existingRoom.getLights());
        assertEquals(updatedRoom.getFans(), existingRoom.getFans());
    }

    @Test
    public void testGetRooms() {
        // Arrange
        RoomRepository mockRoomRepository = mock(RoomRepository.class);
        RoomService roomService = new RoomService(mockRoomRepository);

        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(new Room("Room 1", 1));
        expectedRooms.add(new Room("Room 2", 2));

        // Set up the behavior of the mock repository
        when(mockRoomRepository.findAll()).thenReturn(expectedRooms);

        // Act
        List<Room> result = roomService.getRooms();

        // Assert
        assertEquals(expectedRooms, result);
    }

    @Test
    public void testGetRoomById() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        Room roomToRemove = new Room("Room 1", 1);
        RoomRepository mockRoomRepository = mock(RoomRepository.class);
        when(mockRoomRepository.findById(1)).thenReturn(Optional.of(roomToRemove));
        RoomService roomServiceMock = new RoomService(mockRoomRepository);
        Room roomResult = roomServiceMock.getRoomById(1);

        // Assert
        assertEquals(roomToRemove, roomResult);

        // Check if repository field is initialized
        Field repositoryField = RoomService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        RoomRepository repositoryInstance = (RoomRepository) repositoryField.get(roomServiceMock);
        assertNotNull(repositoryInstance);
    }

    @Test
    public void testSaveRoom() {
        // Arrange
        RoomRepository mockRoomRepository = mock(RoomRepository.class);
        RoomService roomService = new RoomService(mockRoomRepository);

        Room roomToSave = new Room("Room 1", 1);
        Room savedRoom = new Room("Saved Room", 1);

        // Set up the behavior of the mock repository
        when(mockRoomRepository.save(roomToSave)).thenReturn(savedRoom);

        // Act
        Room result = roomService.saveRoom(roomToSave);

        // Assert
        assertEquals(savedRoom, result);
    }

    @Test
    public void testRemoveRoom_ExistingRoom_ReturnsRoom() {
        // Arrange
        int roomId = 1;
        Room room = new Room("Room 1", 100);

        when(repository.findById(roomId)).thenReturn(Optional.of(room));

        // Act
        Room removedRoom = roomService.removeRoom(roomId);

        // Assert
        assertEquals(room, removedRoom);
    }

    @Test
    public void testRemoveRoom_NonExistingRoom_ReturnsNull() {
        // Arrange
        int roomId = 1;

        when(repository.findById(roomId)).thenReturn(Optional.empty());

        // Act
        Room removedRoom = roomService.removeRoom(roomId);

        // Assert
        assertNull(removedRoom);
    }


    @Test
    void testScheduleIntervalCalculation() {
        // sample test data
        List<Room> rooms = new ArrayList<>();
        Room room1 = createSampleRoom("Room 1", 50);
        Room room2 = createSampleRoom("Room 2", 75);
        rooms.add(room1);
        rooms.add(room2);

        EnvironmentData environmentData = createSampleEnvironmentData(25.0);


        // Set up the mock behavior for repository and environmentDataRepository
        Mockito.when(repository.findAll()).thenReturn(rooms);
        Mockito.when(environmentDataRepository.findAll()).thenReturn(List.of(environmentData));

        //instance of RoomService
        RoomService roomService = new RoomService(repository, environmentDataRepository);

        // Call the scheduledIntervalCalculation method
        roomService.scheduledIntervalCalculation();

        // Verify the behavior and assertions
        Mockito.verify(repository, Mockito.times(1)).saveAll(Mockito.anyList());
        // Assert the expected changes in the rooms after the calculation
        assertEquals(2, room1.getTemperatureData().size());
        assertEquals(2, room1.getCo2SensorData().size());

        //Assert the expected values after the calculation
        assertEquals(3, room1.getPeopleData().get(room1.getPeopleData().size() - 1).getCount());
        assertEquals(3.0, room1.getCo2SensorData().get(room1.getCo2SensorData().size() - 1).getcO2value());
        assertEquals(25.0002, room1.getTemperatureData().get(room1.getCo2SensorData().size() - 1).getTemperatureValue());

    }

    @Test
    void testCalculateTemperatureChange() {
        // sample test data
        Room room = createSampleRoom("Room 1", 50);

        // Define the input values for the method
        int numOpenWindows = room.getRoomWindows().size(); // Number of open windows in the room
        int numPeople = room.getPeopleData().get(room.getPeopleData().size() - 1).getCount(); // Number of people in the room
        double avgBodyHeat = 100; // Average body heat (assuming a constant value)
        double outsideTemperature = 25.0; // Outside temperature
        double roomSize = room.getSize(); // Size of the room

        // Calculate the expected temperature change
        double expectedTemperatureChange = (numOpenWindows > 0 ? numOpenWindows : 0.1 * numPeople * avgBodyHeat * outsideTemperature) / (100 * roomSize);
        expectedTemperatureChange = Math.min(Math.max(expectedTemperatureChange, -1), 1);

        // Call the calculateTemperatureChange method
        double actualTemperatureChange = RoomService.calculateTemperatureChange(numOpenWindows, numPeople, avgBodyHeat, outsideTemperature, roomSize);

        // Assert the expected and actual temperature change values
        assertEquals(expectedTemperatureChange, actualTemperatureChange, 0.0001); // Adjust the delta value based on the desired precision

        // Additional assertions and verifications as needed
    }


    private Room createSampleRoom(String name, int size) {
        // sample test data for a room
        List<Door> doors = new ArrayList<>();
        doors.add(new Door (1, true));
        List<Fenster> roomFensters = new ArrayList<>();
        roomFensters.add(new Fenster(1,true));
        List<Light> lights = new ArrayList<>();
        lights.add(new Light(1,true));
        List<Fan> fans = new ArrayList<>();
        fans.add(new Fan(1, true));
        List<Co2SensorData> co2SensorData = new ArrayList<>();
        co2SensorData.add(new Co2SensorData(2.0));
        List<TemperatureData> temperatureData = new ArrayList<>();
        temperatureData.add(new TemperatureData(20.0));
        List<PeopleData> peopleData = new ArrayList<>();
        peopleData.add(new PeopleData(timestamp,3));
        return new Room(name, size, doors, roomFensters, lights, fans, co2SensorData, temperatureData, peopleData);
    }

    private EnvironmentData createSampleEnvironmentData(double outsideTemperature) {
        return new EnvironmentData(outsideTemperature,"noon",SUMMER); // Return the environmentData object
    }


    @Test
    void testAddValues() {
        // sample room
        Room room = createSampleRoom("Room 1", 50);

        // Mock the repository
        RoomRepository repository = Mockito.mock(RoomRepository.class);

        // Set up the mock behavior for the repository
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(room));
        Mockito.when(repository.save(Mockito.any(Room.class))).thenReturn(room);

        // instance of RoomService with the mocked repository
        RoomService roomService = new RoomService(repository);

        // Call the addValues method
        Room updatedRoom = roomService.addValues(room.getId());

        // Verify that the repository methods were called with the correct parameters
        Mockito.verify(repository, Mockito.times(2)).findById(room.getId());
        Mockito.verify(repository, Mockito.times(1)).save(room);

        // Assert that the updatedRoom object is not null
        assertNotNull(updatedRoom);

        // Assert that the room attributes have been updated
        assertEquals(1, updatedRoom.getCo2SensorData().size());
        assertEquals(1, updatedRoom.getTemperatureData().size());
        assertEquals(1, updatedRoom.getPeopleData().size());

    }



}
