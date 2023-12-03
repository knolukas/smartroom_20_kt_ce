package se.smartroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.smartroom.config.LIFXConfig;
import se.smartroom.entities.Room;
import se.smartroom.entities.people.User;
import se.smartroom.entities.people.UserRepository;
import se.smartroom.services.RoomService;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private LIFXConfig lifxConfig; // Assuming you have a reference to LIFXConfig

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/lifx-token")
    public ResponseEntity<String> setUserLIFXToken(@RequestBody String lifxApiToken, @RequestParam int roomId) {
        try {
            Room room = new Room();
            room.findRoomById(roomId);

            if (room != null) {
                // Save the LIFX API token directly in the Room entity
                room.setApiInstanceToken(lifxApiToken);
                return ResponseEntity.ok("LIFX API token set successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found with ID: " + roomId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error setting LIFX API token");
        }
    }

}
