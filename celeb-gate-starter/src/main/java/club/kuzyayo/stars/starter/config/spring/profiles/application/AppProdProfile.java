package club.kuzyayo.stars.starter.config.spring.profiles.application;

import club.kuzyayo.stars.common.configuration.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile({Profiles.PROD_PROFILE, Profiles.DEFAULT_PROFILE})
@PropertySource("classpath:profiles/prod_profile.properties")
public class AppProdProfile {
}
