package se.smartroom.entities.people;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

@Entity
public class User {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String lifxApiToken;

    public User() {
        this.id =null;
    }
    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLifxApiToken() {
        return lifxApiToken;
    }

    public void setLifxApiToken(String lifxApiToken) {
        this.lifxApiToken = lifxApiToken;
    }


}

