package club.kuzyayo.stars.auth.client.config.resources;

import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class AuthorizationCodeClientResources extends AbstractClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

}
