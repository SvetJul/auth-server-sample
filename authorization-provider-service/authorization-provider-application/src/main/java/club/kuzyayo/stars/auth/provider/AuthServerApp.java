package club.kuzyayo.stars.auth.provider;

import club.kuzyayo.stars.starter.config.security.JwtResourceServerConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@SpringBootApplication(exclude = {JwtResourceServerConfigurerAdapter.class,
        org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration.class})
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AuthServerApp extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}