package se.smartroom.entities.LIFXApi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
//import org.springframework.beans.factory.annotation.Value;


@Entity
public class LIFXApi {


    private String apiToken;
    @Id
    @GeneratedValue
    private int id;

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
}
