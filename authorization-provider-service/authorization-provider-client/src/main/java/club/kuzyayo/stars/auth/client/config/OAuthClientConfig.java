package club.kuzyayo.stars.auth.client.config;

import club.kuzyayo.stars.auth.client.config.resources.ResourceOwnerPasswordClientResources;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:oauth2_client.properties")
public class OAuthClientConfig {

    @Bean("cgClientResources")
    @ConfigurationProperties("cg")
    public ResourceOwnerPasswordClientResources celebGateClientResources() {
        return new ResourceOwnerPasswordClientResources();
    }
}
