package se.smartroom.services.LIFXApi;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import se.smartroom.services.RoomService;
import se.smartroom.entities.physicalDevice.Light;

import java.io.IOException;



public class LIFXApi implements LIFX{

    private String apiToken;

    public LIFXApi(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public void turnOnLight(int roomId, String label) throws IOException {
        Light selector = RoomService.getLightSelector(roomId, label);

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("PUT", "https://api.lifx.com/v1/lights/" + selector.getLabel() + "/state")
                .setHeader("accept", "text/plain")
                .setHeader("content-type", "application/json")
                .setHeader("Authorization", apiToken)
                .setBody("{\"duration\":1,\"fast\":false,\"power\":\"on\"}") //only difference is the on here
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    @Override
    public void turnOffLight(int roomId, String label) throws IOException {
        Light selector = RoomService.getLightSelector(roomId, label);

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("PUT", "https://api.lifx.com/v1/lights/" + selector.getLabel() + "/state")
                .setHeader("accept", "text/plain")
                .setHeader("content-type", "application/json")
                .setHeader("Authorization", apiToken)
                .setBody("{\"duration\":1,\"fast\":false,\"power\":\"off\"}") //only difference is the off here
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    public String getApiToken() {
        return apiToken;
    }
}
