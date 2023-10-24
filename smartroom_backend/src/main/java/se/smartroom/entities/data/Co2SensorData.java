package se.smartroom.entities.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Co2SensorData extends DataInterface {

    @Id
    @GeneratedValue
    private int id;

    private Double cO2value;

    public Co2SensorData() {
    }

    public Co2SensorData(Double cO2value) {
        super();
        this.cO2value = cO2value;
    }

    public Co2SensorData(Date timestamp, Double cO2value) {
        super(timestamp);
        this.cO2value = cO2value;
    }

    public Co2SensorData(int id, Date timestamp, Double cO2value) {
        super(timestamp);
        this.id = id;
        this.cO2value = cO2value;
    }

    public Double getcO2value() {
        return cO2value;
    }

    public void setcO2value(Double cO2value) {
        this.cO2value = cO2value;
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
        Co2SensorData that = (Co2SensorData) o;
        return id == that.id && Objects.equals(cO2value, that.cO2value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cO2value);
    }

    @Override
    public String toString() {
        return "TemperatureData{" +
                "id=" + id +
                ", cO2value=" + cO2value +
                '}';
    }
}
