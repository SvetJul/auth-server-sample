package club.kuzyayo.stars.starter.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Import(ResourceServerConfig.class)
@Configuration
@AllArgsConstructor
@EnableResourceServer
public class JwtResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    private final TokenStore jwtTokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/rest/api/admin/**", "/rest/api/system/**")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

}
