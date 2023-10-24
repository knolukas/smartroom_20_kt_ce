package se.smartroom.entities.physicalDevice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Door extends PhysicalDevice {
    @Id
    @GeneratedValue
    private int id;

    public Door() {
    }

    public Door(boolean open) {super(open);}

    public Door(int id, boolean open) {
        /*super(open);*/
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
        return "Door{" +
                "id=" + id +
                ", open=" + super.isOpen() +
                '}';
    }
}
