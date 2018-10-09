package club.kuzyayo.stars.auth.client.rest.template;

import club.kuzyayo.stars.auth.client.config.OAuthClientConfig;
import club.kuzyayo.stars.auth.client.config.resources.ResourceOwnerPasswordClientResources;
import club.kuzyayo.stars.auth.client.system.user.SystemUserClientContext;
import club.kuzyayo.stars.common.configuration.JacksonConverterConfig;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ComponentScan(basePackageClasses = SystemUserClientContext.class)
@Import({JacksonConverterConfig.class, OAuthClientConfig.class})
@Configuration
public class SystemUserAuthenticationRestTemplateConfig {


    @Scope("prototype")
    @Bean(name = "systemRestOperations")
    public RestOperations systemRestOperations(List<HttpMessageConverter<?>> converters,
                                               ResourceOwnerPasswordClientResources celebGateClientResources,
                                               SystemUserClientContext clientContext) {
        RestTemplate restTemplate = new OAuth2RestTemplate(celebGateClientResources.getClient(), clientContext);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

}
