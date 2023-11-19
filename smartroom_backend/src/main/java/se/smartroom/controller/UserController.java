package se.smartroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.smartroom.config.LIFXConfig;
import se.smartroom.entities.people.User;
import se.smartroom.entities.people.UserRepository;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private LIFXConfig lifxConfig; // Assuming you have a reference to LIFXConfig

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/lifx-token")
    public ResponseEntity<String> setUserLIFXToken(@RequestBody String lifxApiToken) {
        try {
            // Save the LIFX API token in the temporary MariaDB container
            lifxConfig.setLifxApiToken(lifxApiToken);

            // User with a hardcoded username for now
            String username = "exampleUser";
            User user = userRepository.findByUsername(username);

            if (user == null) {
                user = new User();
                user.setUsername(username);
            }

            user.setLifxApiToken(lifxApiToken);
            userRepository.save(user);

            return ResponseEntity.ok("LIFX API token set successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error setting LIFX API token");
        }
    }
}
