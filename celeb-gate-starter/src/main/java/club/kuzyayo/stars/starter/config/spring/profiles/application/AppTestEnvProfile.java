package club.kuzyayo.stars.starter.config.spring.profiles.application;

import club.kuzyayo.stars.common.configuration.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile({Profiles.TEST_ENV_PROFILE})
@PropertySource("classpath:profiles/test_env_profile.properties")
public class AppTestEnvProfile {

}
