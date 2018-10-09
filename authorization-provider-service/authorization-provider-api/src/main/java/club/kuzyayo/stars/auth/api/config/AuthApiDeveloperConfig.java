package club.kuzyayo.stars.auth.api.config;

import club.kuzyayo.stars.common.configuration.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile({Profiles.DEVELOPER_PROFILE})
@Configuration
@PropertySource("classpath:profiles/auth_api_developer_profile.properties")
public class AuthApiDeveloperConfig {
}
