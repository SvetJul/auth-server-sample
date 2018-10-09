package club.kuzyayo.stars.starter.config.spring.profiles.application;

import club.kuzyayo.stars.common.configuration.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile({Profiles.DEVELOPER_PROFILE})
@PropertySource("classpath:profiles/developer_profile.properties")
public class AppDeveloperProfile {

}
