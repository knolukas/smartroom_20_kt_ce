package se.smartroom.entities.people;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class PeopleData {

    @Id
    @GeneratedValue
    private int id;

    @Temporal(TemporalType.DATE)
    private Date timestamp;

    private int count;

    public PeopleData() {
    }
    public PeopleData(Date timestamp, int count) {
        this.timestamp = timestamp;
        this.count = count;
    }

    public PeopleData(int id, Date timestamp, int count) {
        this.id = id;
        this.timestamp = timestamp;
        this.count = count;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleData that = (PeopleData) o;
        return id == that.id && count == that.count && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, count);
    }

    @Override
    public String toString() {
        return "PeopleData{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", count=" + count +
                '}';
    }
}
