package club.kuzyayo.stars.auth.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AuthApiCommonConfig.class,
        AuthApiDeveloperConfig.class,
        AuthApiTestEnvConfig.class,
})
@Configuration
public class AuthorizationProviderApiConfig {
}
