package se.smartroom.entities.physicalDevice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import se.smartroom.entities.smartDevice.SmartDevice;

@Entity
public class Light extends PhysicalDevice {
//public class Light extends SmartDevice {

    @Id
    @GeneratedValue
    private int id;

    public Light() {
    }

    public Light(boolean open) {
        super(open);
    }

    public Light(int id, boolean open) {
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
        return "Light{" +
                "id=" + id +
                ", on=" + super.isOpen() +
                '}';
    }
}
