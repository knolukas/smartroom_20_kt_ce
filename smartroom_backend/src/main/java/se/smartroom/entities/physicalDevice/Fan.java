package se.smartroom.entities.physicalDevice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import se.smartroom.entities.smartDevice.SmartDevice;

@Entity
public class Fan extends PhysicalDevice {
    @Id
    @GeneratedValue
    private int id;

    public Fan() {
    }

    public Fan(boolean open) {
        super(open);
    }

    public Fan(int id, boolean open) {
        super(open);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fan{" +
                "id=" + id +
                ", on=" + this.isOpen() +
                '}';
    }
}
