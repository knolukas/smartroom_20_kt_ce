package se.smartroom.tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import se.smartroom.controller.RoomController;
import se.smartroom.entities.Room;
import se.smartroom.services.RoomService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {
//Arrange = Creation of instances

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }
    @Test
    public void testGetRooms() throws Exception {
        // list of Room objects for mocking the response
        List<Room> mockedRooms = new ArrayList<>();
        mockedRooms.add(new Room("Room 1",45));
        mockedRooms.add(new Room("Room 2",23));

        // Configure the behavior of the mock
        when(roomService.getRooms()).thenReturn(mockedRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/rooms"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Room 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].size").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Room 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].size").value(23));
    }

    @Test
    public void testGetRoom() throws Exception {
        // Arrange
        int roomId = 1;
        Room expectedRoom = new Room("Room 1", 15);

        // Mock the behavior of the roomService.getRoomById() method
        Mockito.when(roomService.getRoomById(roomId)).thenReturn(expectedRoom);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/room/{id}", roomId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(15));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        // Arrange
        Room roomToUpdate = new Room("Room 1", 13);

        // Mock the behavior of the roomService.updateRoom() method
        Mockito.when(roomService.updateRoom(Mockito.any(Room.class))).thenReturn(roomToUpdate);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(roomToUpdate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(13));
    }

    @Test
    public void testCreateRoom() throws Exception {
        // Arrange
        Room roomToCreate = new Room("Room 1", 12);

        // Mock the behavior of the roomService.saveRoom() method
        Mockito.when(roomService.saveRoom(Mockito.any(Room.class))).thenReturn(roomToCreate);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(roomToCreate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(12));
    }

    @Test
    public void testDeleteRoom() throws Exception {
        // Arrange
        int roomId = 1;

        // Mock the behavior of the roomService.removeRoom() method
        Mockito.when(roomService.removeRoom(Mockito.eq(roomId))).thenReturn(null);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/room/{id}", roomId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Arrange
        List<Room> mockedRooms = new ArrayList<>();
        mockedRooms.add(new Room("Room 1", 1));
        mockedRooms.add(new Room("Room 2", 2));
        Mockito.when(roomService.getRooms()).thenReturn(mockedRooms);


        // Create a mock HttpServletResponse
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(printWriter);

        // Act
        roomController.exportToCSV(mockResponse);

        // Assert
        verify(mockResponse).setContentType("text/csv");

        // Verify the header key and value
        ArgumentCaptor<String> headerKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockResponse).setHeader(headerKeyCaptor.capture(), headerValueCaptor.capture());
        assertEquals("Content-Disposition", headerKeyCaptor.getValue());
        assertTrue(headerValueCaptor.getValue().startsWith("attachment; filename=rooms_"));

        // Verify the CSV content
        printWriter.flush();
        String csvContent = stringWriter.toString();

        String expectedHeader = "Room ID,Room Name,Room Size,Doors,Windows,Lights,Fans,co2SensorData,temperatureData";
        String[] csvRows = csvContent.split("\r?\n");
        String[] actualHeader = csvRows[0].split(",");

        // Remove any trailing "\r" from the expected header
        expectedHeader = expectedHeader.replaceAll("\r$", "");

        // Assert that the headers are equal
        assertArrayEquals(expectedHeader.split(","), actualHeader);

        // Verify the CSV data
        for (int i = 0; i < mockedRooms.size(); i++) {
            String[] expectedData = {
                    String.valueOf(mockedRooms.get(i).getId()),
                    mockedRooms.get(i).getName(),
                    String.valueOf(mockedRooms.get(i).getSize()),
                    mockedRooms.get(i).getDoors().toString(),
                    mockedRooms.get(i).getRoomWindows().toString(),
                    mockedRooms.get(i).getLights().toString(),
                    mockedRooms.get(i).getFans().toString(),
                    mockedRooms.get(i).getCo2SensorData().toString(),
                    mockedRooms.get(i).getTemperatureData().toString()
            };
            String[] actualData = csvRows[i + 1].split(",");
            assertArrayEquals(expectedData, actualData);
        }

    }
}
