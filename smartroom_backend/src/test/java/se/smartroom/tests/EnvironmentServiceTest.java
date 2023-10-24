package se.smartroom.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.entities.environment.SEASONSTATUS;
import se.smartroom.repositories.EnvironmentDataRepository;
import se.smartroom.services.EnvironmentDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnvironmentServiceTest {
    //Arrange = Creation of instances
    @InjectMocks
    private EnvironmentDataService environmentDataService;
    @Mock
    private EnvironmentDataRepository mockDataRepository;

    @Test
    public void testSaveEnvironment() {
        // sample environmentData object for testing
        EnvironmentData environmentData = new EnvironmentData();
        environmentData.setId(1);
        environmentData.setOutsideTemperature(25.0);
        environmentData.setSeasonStatus(SEASONSTATUS.SUMMER);

        // instance of the service to be tested, with the mock repository injected
        EnvironmentDataService dataService = new EnvironmentDataService(mockDataRepository);

        // Set up the behavior of the mock repository
        when(mockDataRepository.save(environmentData)).thenReturn(environmentData);

        // Call the method under test
        EnvironmentData savedEnvironmentData = dataService.saveEnvironment(environmentData);

        // Perform assertions
        assertEquals(environmentData, savedEnvironmentData);
        assertEquals(savedEnvironmentData.getSeasonstatus(),SEASONSTATUS.SUMMER);
        assertEquals(environmentData.getSeasonstatus(),savedEnvironmentData.getSeasonstatus());
        assertEquals(environmentData.getId(),savedEnvironmentData.getId());
    }

    @Test
    public void testRemoveEnvironment() {
        // sample environmentData object for testing
        int environmentId = 1;
        EnvironmentData environmentData = new EnvironmentData(1,22.0,"noon",SEASONSTATUS.SPRING);

        // instance of the service to be tested, with the mock repository injected
        EnvironmentDataService dataService = new EnvironmentDataService(mockDataRepository);

        // Set up the behavior of the mock repository
        when(mockDataRepository.findById(environmentId)).thenReturn(Optional.of(environmentData));

        // Call the method under test
        EnvironmentData removedEnvironmentData = dataService.removeEnvironment(environmentId);

        // Verify the interactions
        verify(mockDataRepository, times(1)).findById(environmentId);
        verify(mockDataRepository, times(1)).delete(environmentData);

        // Perform assertion
        assertEquals(environmentData, removedEnvironmentData);
    }
    @Test
    public void testScheduledIntervalCalculation() {
        // Arrange
        List<EnvironmentData> environmentDataList = new ArrayList<>();
        environmentDataList.add(new EnvironmentData(20.0, "08:55", SEASONSTATUS.SUMMER));

        when(mockDataRepository.findAll()).thenReturn(environmentDataList);

        // Act
        environmentDataService.scheduledIntervalCalculation();

        // Assert
        verify(mockDataRepository, times(1)).findAll();
        verify(mockDataRepository, times(1)).save(any(EnvironmentData.class));
    }

}
