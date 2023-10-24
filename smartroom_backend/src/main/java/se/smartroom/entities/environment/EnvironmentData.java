package se.smartroom.entities.environment;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class EnvironmentData {

    @Id
    @GeneratedValue
    private int id;

    private double outsideTemperature;

    private String timeOfTheDay;

    private SEASONSTATUS seasonstatus;

    public EnvironmentData() {
    }

    public EnvironmentData(double outsideTemperature, String timeOfTheDay, SEASONSTATUS seasonstatus) {
        this.outsideTemperature = outsideTemperature;
        this.timeOfTheDay = timeOfTheDay;
        this.seasonstatus = seasonstatus;
    }

    public EnvironmentData(int id, double outsideTemperature, String timeOfTheDay, SEASONSTATUS seasonstatus) {
        this.id = id;
        this.outsideTemperature = outsideTemperature;
        this.timeOfTheDay = timeOfTheDay;
        this.seasonstatus = seasonstatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public String getTimeOfTheDay() {
        return timeOfTheDay;
    }

    public void setTimeOfTheDay(String timeOfTheDay) {
        this.timeOfTheDay = timeOfTheDay;
    }

    public SEASONSTATUS getSeasonstatus() {
        return seasonstatus;
    }

    public void setSeasonStatus(SEASONSTATUS seasonstatus) {
        this.seasonstatus = seasonstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentData that = (EnvironmentData) o;
        return id == that.id && Double.compare(that.outsideTemperature, outsideTemperature) == 0 && Objects.equals(timeOfTheDay, that.timeOfTheDay) && seasonstatus == that.seasonstatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, outsideTemperature, timeOfTheDay, seasonstatus);
    }

    @Override
    public String toString() {
        return "EnvironmentData{" +
                "id=" + id +
                ", outsideTemperature=" + outsideTemperature +
                ", timeOfTheDay=" + timeOfTheDay +
                ", seasonstatus=" + seasonstatus +
                '}';
    }
}
