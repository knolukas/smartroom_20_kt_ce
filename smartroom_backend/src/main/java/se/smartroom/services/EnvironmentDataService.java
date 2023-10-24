package se.smartroom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.entities.environment.SEASONSTATUS;
import se.smartroom.repositories.EnvironmentDataRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EnvironmentDataService {

    @Autowired
    private EnvironmentDataRepository repository;

    @Value("${environment.temp}")
    public double temp;
    @Value("${environment.minTemp}")
    public double minTemp;
    @Value("${environment.maxTemp}")
    public double maxTemp;
    @Value("${environment.time.intervals}")
    public int intervals;

    public String time;

    @Value("${environment.time}")
    public void setTime(String time) {
        this.time = time;
    }


    public EnvironmentDataService(EnvironmentDataRepository mockDataRepository) {
        repository = mockDataRepository;
    }

    public EnvironmentData saveEnvironment(EnvironmentData environmentData) {
        return repository.save(environmentData);
    }

    public EnvironmentData removeEnvironment(int id) {
        EnvironmentData environmentData = repository.findById(id).orElse(null);

        if (environmentData != null) {
            repository.delete(environmentData);
        }

        return environmentData;
    }

    public List<EnvironmentData> getEnvironments() {
        return repository.findAll();
    }

    public EnvironmentData getEnvironmentById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void scheduledIntervalCalculation() {
        EnvironmentData environment;

        List<EnvironmentData> environmentDataList = repository.findAll();
        if (environmentDataList.isEmpty()) {

            EnvironmentData environmentData = new EnvironmentData(
                    this.temp,
                    this.time,
                    SEASONSTATUS.SUMMER
            );
            environment = repository.save(environmentData);
        } else {
            environment = environmentDataList.get(0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Update the format
        String trimmedTime = environment.getTimeOfTheDay().trim().replace("\"", "");
        LocalTime newTimeOfTheDay = LocalTime.parse(trimmedTime, formatter);
        newTimeOfTheDay = newTimeOfTheDay.plusMinutes(this.intervals);
        environment.setTimeOfTheDay(newTimeOfTheDay.toString());

        int hour = newTimeOfTheDay.getHour();
        if (hour >= 0 && hour < 6) {
            environment.setOutsideTemperature(Math.max(environment.getOutsideTemperature() - 1.0, this.minTemp));
        } else if (hour >= 6 && hour < 18) {
            environment.setOutsideTemperature(Math.min(environment.getOutsideTemperature() + 1.0, this.maxTemp));
        } else if (hour >= 18 && hour <= 23) {
            environment.setOutsideTemperature(Math.max(environment.getOutsideTemperature() - 1.0, this.minTemp));
        }

        repository.save(environment);

       // System.out.println("Environment Data updated");
       // System.out.println(environment.toString());
    }

}
