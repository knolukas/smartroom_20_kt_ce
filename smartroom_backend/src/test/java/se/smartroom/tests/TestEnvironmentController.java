package se.smartroom.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.smartroom.controller.EnvironmentDataController;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.entities.environment.SEASONSTATUS;
import se.smartroom.services.EnvironmentDataService;

import java.util.List;
import java.util.ArrayList;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestEnvironmentController {
    @Mock
    private EnvironmentDataService environmentService;

    @InjectMocks
    private EnvironmentDataController environmentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(environmentController).build();
    }

    @Test
    public void testGetEnvironments() throws Exception {
        // list of Environment objects for mocking the response
        List<EnvironmentData> mockedEnvironments = new ArrayList<>();
        mockedEnvironments.add(new EnvironmentData(0,18, "noon", SEASONSTATUS.SPRING));
        mockedEnvironments.add(new EnvironmentData(1,11, "midnight", SEASONSTATUS.SPRING));

        // Configure the behavior of the mock
        when(environmentService.getEnvironments()).thenReturn(mockedEnvironments);

        mockMvc.perform(MockMvcRequestBuilders.get("/environment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].outsideTemperature").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timeOfTheDay").value("noon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timeOfTheDay").value("midnight"));
    }

    @Test
    public void testGetEnvironment() throws Exception {
        int environmentId = 1;
        EnvironmentData mockedEnvironment = new EnvironmentData(environmentId, 25, "morning", SEASONSTATUS.SUMMER);

        // Configure the behavior of the mock
        when(environmentService.getEnvironmentById(environmentId)).thenReturn(mockedEnvironment);

        mockMvc.perform(MockMvcRequestBuilders.get("/environment/{id}", environmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(environmentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outsideTemperature").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeOfTheDay").value("morning"));
    }


    @Test
    public void testDeleteEnvironment() throws Exception {
        int environmentId = 1;
        EnvironmentData deletedEnvironment = new EnvironmentData(environmentId, 25, "morning", SEASONSTATUS.SUMMER);

        // Configure the behavior of the mock
        when(environmentService.removeEnvironment(environmentId)).thenReturn(deletedEnvironment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/environment/{id}", environmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(environmentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outsideTemperature").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeOfTheDay").value("morning"));
    }



}
