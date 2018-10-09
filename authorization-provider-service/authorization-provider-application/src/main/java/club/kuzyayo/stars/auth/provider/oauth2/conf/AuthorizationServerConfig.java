package club.kuzyayo.stars.auth.provider.oauth2.conf;

import club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type.CustomTokenGranter;
import club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type.InternalGrantTypeAuthenticationProvider;
import club.kuzyayo.stars.auth.provider.oauth2.conf.token.config.CustomUserAuthenticationConverter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth.clientId}")
    private String clientId;
    @Value("${oauth.clientSecret}")
    private String clientSecret;
    @Value("${auth.signingKey}")
    private String signingKey;


    @Value("${oauth.access-token-validity-seconds}")
    private int accessTokenValiditySeconds;
    @Value("${oauth.refresh-token-validity-seconds}")
    private int refreshTokenValiditySeconds;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenGranter(tokenGranter(endpoints, authenticationManager()))
                .authenticationManager(authenticationManager())
                .userDetailsService(userDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .authorizedGrantTypes("password", "refresh_token", "client_credentials", "internal")
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .scopes("openid");
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        InternalGrantTypeAuthenticationProvider internalGrantTypeAuthenticationProvider = new InternalGrantTypeAuthenticationProvider();
        internalGrantTypeAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider, internalGrantTypeAuthenticationProvider));
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        val converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        converter.setAccessTokenConverter(accessTokenConverter);
        converter.setSigningKey(signingKey);
        return converter;
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints,
                                      AuthenticationManager authenticationManager) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new CustomTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(),
                authenticationManager));
        return new CompositeTokenGranter(granters);
    }

}
