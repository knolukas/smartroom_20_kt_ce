package se.smartroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.smartroom.entities.LIFXApi.LIFXApi;

@Configuration
public class LIFXConfig {
    /*
    @Value("${lifx.api.token}")
    private String lifxApiTokenIntern;
*/
    private String lifxApiTokenExtern;

    @Bean
    public LIFXApi lifxApiExtern() {
        // Instantiate and configure the LIFXApiImpl class using the token dynamically
        return new LIFXApi(getUserSpecificToken()); // Assuming LIFXApi has a constructor that takes the token
    }
    /*
    @Bean
    public LIFXApi lifxApiIntern() {
        // Instantiate and configure the LIFXApi class using the injected token
        return new LIFXApi(lifxApiTokenIntern);
    }
    */


    private String getUserSpecificToken() {
        // Implement logic to retrieve the user-specific LIFX API token

        return lifxApiTokenExtern;
    }

    public void setLifxApiToken(String lifxApiToken) {
        this.lifxApiTokenExtern = lifxApiToken;
    }
}
