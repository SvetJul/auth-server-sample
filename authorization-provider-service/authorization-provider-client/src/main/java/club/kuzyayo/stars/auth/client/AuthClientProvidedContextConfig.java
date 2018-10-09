package club.kuzyayo.stars.auth.client;

import club.kuzyayo.stars.auth.client.rest.template.SystemUserAuthenticationRestTemplateConfig;
import club.kuzyayo.stars.auth.client.service.ExternalUserAccountService;
import club.kuzyayo.stars.auth.client.token.provider.AccessTokenProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackageClasses = {
        AccessTokenProvider.class,
        ExternalUserAccountService.class})
@Import(SystemUserAuthenticationRestTemplateConfig.class)
@Configuration
public class AuthClientProvidedContextConfig {
}
