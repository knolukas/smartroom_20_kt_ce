package se.smartroom.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import se.smartroom.entities.Room;
import se.smartroom.entities.physicalDevice.Light;
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

    @PostMapping("/room/{id}/lights/on")
    public ResponseEntity<String> turnOnLights(@PathVariable int id, @RequestBody Light light) {
        System.out.println("Enter lights on");
        System.out.println(light);
        String lightLabel = light.getLabel();
        String lightId = String.valueOf(light.getLifxId());
        //hier überschreiben für Test
        String token = "c539309865aa41bd1e99b06df6e9ba66a328b8c176c9dea2762614aec75df406"; //TODO: token aus config holen @Henrique
        lightLabel = "NightLight";
        lightId = "d073d556463a";
        try {
            roomService.turnOnLights(id, lightLabel,lightId, token);
            return ResponseEntity.ok("Lights turned on successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to turn on lights: " + e.getMessage());
        }
    }

    @PostMapping("/room/{id}/lights/update_color")
    public ResponseEntity<String> updateColour(@PathVariable int id,@RequestParam String colorId, @RequestBody Light light) {
        System.out.println("Enter update color");
        System.out.println(light);
        String lightLabel = light.getLabel();
        String lightId = String.valueOf(light.getLifxId());
        String token = light.getToken();
//        String token = "c539309865aa41bd1e99b06df6e9ba66a328b8c176c9dea2762614aec75df406"; //TODO: token aus config holen @Henrique
//        lightLabel = "NightLight";
//        lightId = "d073d556463a";
        try {
            roomService.updateColor(id, lightLabel, lightId, token, colorId);
            return ResponseEntity.ok("Light color updated");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to turn off lights: " + e.getMessage());
        }
    }

    @PostMapping("/room/{id}/lights/off")
    public ResponseEntity<String> turnOffLights(@PathVariable int id, @RequestBody Light light) {
        System.out.println("Enter lights off");
        System.out.println(light);
        String lightLabel = light.getLabel();
        String lightId = String.valueOf(light.getLifxId());
        //hier überschreiben für Test
        String token = "c539309865aa41bd1e99b06df6e9ba66a328b8c176c9dea2762614aec75df406"; //TODO: token aus config holen @Henrique
        lightLabel = "NightLight";
        lightId = "d073d556463a";
        try {
            roomService.turnOffLights(id, lightLabel, lightId, token);
            return ResponseEntity.ok("Lights turned off successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to turn off lights: " + e.getMessage());
        }
    }

    @PostMapping("/room/{id}/add_light")
    public ResponseEntity<String> addLight(@PathVariable int id, @RequestParam String label){
        Room room = roomService.getRoomById(id);
        if (room != null) {
            Light newLight = new Light(label, false);
            room.addLight(newLight);

            // Save the updated room with the new light to the database
            roomService.saveRoom(room);

            return ResponseEntity.ok("Added new light id: " + newLight.getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found with ID: " + id);
        }
    }



}

