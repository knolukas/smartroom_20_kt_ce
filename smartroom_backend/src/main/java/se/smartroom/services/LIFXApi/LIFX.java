package se.smartroom.services.LIFXApi;

import se.smartroom.entities.physicalDevice.Light;

import java.io.IOException;

public interface LIFX {
    void turnOnLight(int roomId, String label) throws IOException;
    public void turnOffLight(int roomId, String label)throws IOException;
}
