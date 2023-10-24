package se.smartroom.entities.physicalDevice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//this is the only class in German due to the incompatibility with MARIADB
//in MariaDB Window is a keyword and therefore cannot be used as a class name
@Entity
public class Fenster extends PhysicalDevice {
    @Id
    @GeneratedValue
    private int id;

    public Fenster() {
    }

    public Fenster(boolean open) {
        super(open);
    }

    public Fenster(int id, boolean open) {
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
        return "Window{" +
                "id=" + id +
                ", open=" + isOpen() +
                '}';
    }
}
