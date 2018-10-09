package club.kuzyayo.stars.starter.config.security;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@PropertySource("classpath:auth-provider.properties")
public class ResourceServerConfig {

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(@Value("${auth.signingKey}") String signingKey) {
        val converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomAuthenticationExtractor());
        converter.setAccessTokenConverter(accessTokenConverter);
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public DefaultTokenServices tokenServices(TokenStore jwtTokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jwtTokenStore);
        return tokenServices;
    }

}
