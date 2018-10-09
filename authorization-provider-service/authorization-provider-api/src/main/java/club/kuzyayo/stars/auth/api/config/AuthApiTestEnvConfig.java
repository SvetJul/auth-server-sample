package club.kuzyayo.stars.auth.api.config;

import club.kuzyayo.stars.common.configuration.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile({Profiles.TEST_ENV_PROFILE, Profiles.PROD_PROFILE, Profiles.DEFAULT_PROFILE})
@Configuration
@PropertySource("classpath:profiles/auth_api_test_env_profile.properties")
public class AuthApiTestEnvConfig {
}
