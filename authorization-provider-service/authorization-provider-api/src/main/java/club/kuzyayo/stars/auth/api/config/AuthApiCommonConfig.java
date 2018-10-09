package club.kuzyayo.stars.auth.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:profiles/auth_api_common.properties")
public class AuthApiCommonConfig {
}
