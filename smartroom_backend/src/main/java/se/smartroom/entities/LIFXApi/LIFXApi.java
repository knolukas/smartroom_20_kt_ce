package se.smartroom.entities.LIFXApi;

import jakarta.persistence.*;
import se.smartroom.entities.Room;
//import org.springframework.beans.factory.annotation.Value;


@Entity
public class LIFXApi {


    private String apiToken;
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public LIFXApi(String apiToken) {
        this.apiToken = apiToken;
    }

    public LIFXApi(int id) {
        this.id=id;
    }

    public LIFXApi() {
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
