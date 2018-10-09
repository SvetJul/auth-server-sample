package club.kuzyayo.stars.starter.config.spring.profiles;


import club.kuzyayo.stars.starter.config.spring.profiles.application.AppDeveloperProfile;
import club.kuzyayo.stars.starter.config.spring.profiles.application.AppProdProfile;
import club.kuzyayo.stars.starter.config.spring.profiles.application.AppTestEnvProfile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        AppDeveloperProfile.class,
        AppTestEnvProfile.class,
        AppProdProfile.class,
})
@Configuration
public class ProfileAggregationConfig {

}
