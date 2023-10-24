package se.smartroom.entities.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class TemperatureData extends DataInterface {

    @Id
    @GeneratedValue
    private int id;

    private Double temperatureValue;

    public TemperatureData() {
    }

    public TemperatureData(Double temperatureValue) {
        super();
        this.temperatureValue = temperatureValue;
    }

    public TemperatureData(Date timestamp, Double temperatureValue) {
        super(timestamp);
        this.temperatureValue = temperatureValue;
    }

    public TemperatureData(int id, Date timestamp, Double temperatureValue) {
        super(timestamp);
        this.id = id;
        this.temperatureValue = temperatureValue;
    }

    public Double getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(Double temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TemperatureData that = (TemperatureData) o;
        return id == that.id && Objects.equals(temperatureValue, that.temperatureValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, temperatureValue);
    }

    @Override
    public String toString() {
        return "Co2SensorData{" +
                "id=" + id +
                ", temperaturValue=" + temperatureValue +
                '}';
    }
}
