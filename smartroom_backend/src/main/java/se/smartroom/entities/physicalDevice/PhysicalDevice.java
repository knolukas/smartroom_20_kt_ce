package se.smartroom.entities.physicalDevice;

import jakarta.persistence.MappedSuperclass;

import javax.security.sasl.SaslServer;
import java.util.Objects;

@MappedSuperclass
public class PhysicalDevice {
    private boolean open;

    public PhysicalDevice() {
    }

    public PhysicalDevice(boolean open) {
        this.open = open;
        //System.out.println(this + " Open ->" + this.open);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
       // System.out.println(this + " Open ->" + this.open);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalDevice that = (PhysicalDevice) o;
        return open == that.open;
    }

    @Override
    public int hashCode() {
        return Objects.hash(open);
    }

    @Override
    public String toString() {
        return "PhysicalDevice{" +
                "open=" + open +
                '}';
    }
}
