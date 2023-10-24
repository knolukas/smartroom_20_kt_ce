package se.smartroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.services.EnvironmentDataService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EnvironmentDataController {

    @Autowired
    private EnvironmentDataService environmentDataService;


    @GetMapping("/environment")
    public List<EnvironmentData> getEnvironments() {
        return environmentDataService.getEnvironments();
    }

    @GetMapping("/environment/{id}")
    public EnvironmentData getEnvironment(@PathVariable int id) {
        return environmentDataService.getEnvironmentById(id);
    }

    @PostMapping("/environment")
    public EnvironmentData createEnvironment(@RequestBody EnvironmentData environmentData) {
        return environmentDataService.saveEnvironment(environmentData);
    }

    @DeleteMapping("/environment/{id}")
    public EnvironmentData deleteEnvironment(@PathVariable int id) {
        return environmentDataService.removeEnvironment(id);
    }

}
