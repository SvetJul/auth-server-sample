package club.kuzyayo.stars.auth.client.config.resources;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

public abstract class AbstractClientResources {

    @NestedConfigurationProperty
    protected ResourceServerProperties resource = new ResourceServerProperties();

    public ResourceServerProperties getResource() {
        return resource;
    }

    public abstract BaseOAuth2ProtectedResourceDetails getClient();

}
