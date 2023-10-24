package se.smartroom.entities.data;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.sql.Date;
import java.util.Objects;

public class DataInterface {

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public DataInterface() {
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public DataInterface(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataInterface that = (DataInterface) o;
        return Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public String toString() {
        return "DataInterface{" +
                "timestamp=" + timestamp +
                '}';
    }
}
