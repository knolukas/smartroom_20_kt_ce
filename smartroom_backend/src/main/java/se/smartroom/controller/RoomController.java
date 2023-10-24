package se.smartroom.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import se.smartroom.entities.Room;
import se.smartroom.services.RoomService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/room/{id}")
    public Room getRoom(@PathVariable int id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/room")
    public Room updateRoom(@RequestBody Room room) {

        //System.out.println("Test: ");
        //System.out.println(room);

        return roomService.updateRoom(room);
    }

    @PostMapping("/room")
    public Room createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    @DeleteMapping("/room/{id}")
    public Room deleteRoom(@PathVariable int id) {
        return roomService.removeRoom(id);
    }

    @PutMapping("room/{id}/addValues")
    public Room addValues(@PathVariable int id) { return roomService.addValues(id);}


    @GetMapping("/rooms/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv"); //set format
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        // set the name of the files that will be dowload, which will also have a date stamp
        String headerValue = "attachment; filename=rooms_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Room> listRooms = roomService.getRooms();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Room ID","Room Name", "Room Size", "Doors", "Windows", "Lights","Fans","co2SensorData","temperatureData"};
        String[] nameMapping = {"id","name", "size", "doors", "roomWindows", "lights","fans","co2SensorData","temperatureData"};
        csvWriter.writeHeader(csvHeader);

        for (Room rooms : listRooms) {
            csvWriter.write(rooms, nameMapping);
        }
        csvWriter.close();

    }

}

