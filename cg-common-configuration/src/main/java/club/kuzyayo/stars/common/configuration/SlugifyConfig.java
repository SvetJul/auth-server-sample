package club.kuzyayo.stars.common.configuration;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifyConfig {

    @Bean
    public Slugify slugify() {
        Slugify slugify = new Slugify();
        slugify.withCustomReplacement("ä", "a");
        return slugify;
    }

}
