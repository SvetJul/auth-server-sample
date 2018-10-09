package club.kuzyayo.stars.auth.client.system.user;

import club.kuzyayo.stars.auth.api.dto.Role;
import club.kuzyayo.stars.auth.client.config.resources.ResourceOwnerPasswordClientResources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
@RequiredArgsConstructor
public class SystemUserClientContext implements OAuth2ClientContext {

    private volatile OAuth2AccessToken accessToken;

    @Value("${system.username}")
    private String username;

    @Value("${auth.signingKey}")
    private String signingKey;

    private final ObjectMapper objectMapper;
    private final ResourceOwnerPasswordClientResources celebGateClientResources;

    @PostConstruct
    public void provideToken() {
        HashMap<String, String> tokenParams = new HashMap<>();
        tokenParams.put(UserAuthenticationConverter.USERNAME, username);
        tokenParams.put(UserAuthenticationConverter.AUTHORITIES, Role.Constants.ROLE_SYSTEM);
        tokenParams.put(AccessTokenConverter.CLIENT_ID, celebGateClientResources.getClient().getClientId());
        String tokenValue = JwtHelper.encode(writeAsString(tokenParams), new MacSigner(signingKey)).getEncoded();
        accessToken = new DefaultOAuth2AccessToken(tokenValue);
        log.debug("Access token = {}", tokenValue);
    }


    @Override
    public OAuth2AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {

    }

    @Override
    public AccessTokenRequest getAccessTokenRequest() {
        return null;
    }

    @Override
    public void setPreservedState(String stateKey, Object preservedState) {

    }

    @Override
    public Object removePreservedState(String stateKey) {
        return null;
    }

    private String writeAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Object serialization failed: object.toString() = %s",
                    String.valueOf(value)), e);
        }
    }

}
