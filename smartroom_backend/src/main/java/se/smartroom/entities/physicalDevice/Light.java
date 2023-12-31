package se.smartroom.entities.physicalDevice;

import jakarta.persistence.Column;
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

    private String label;

    @Column(nullable = false)
    private boolean is_on = false;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Light() {
    }

    public Light(boolean open) {
        super(open);
    }

    public Light(int id, boolean open){
        super(open);
        this.id = id;
    }
    public Light(String label, boolean open){
        super(open);
        this.label = label;
    }


    public Light(int id, boolean open, String label) {
        super(open);
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOn() {
        return is_on;
    }
    public void setOn(boolean open) {
        this.is_on = open;
    }

    @Override
    public String toString() {
        return "Light{" +
                "id=" + id +
                ", on=" + super.isOpen() +
                '}';
    }



}
