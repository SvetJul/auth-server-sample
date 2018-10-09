package club.kuzyayo.stars.auth.client.config.resources;

import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

public class ResourceOwnerPasswordClientResources extends AbstractClientResources {

    @NestedConfigurationProperty
    private ResourceOwnerPasswordResourceDetails client = new ResourceOwnerPasswordResourceDetails();

    public ResourceOwnerPasswordResourceDetails getClient() {
        return client;
    }
}
