package club.kuzyayo.stars.auth.client.token.provider;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
public class AccessTokenProviderImpl implements AccessTokenProvider {

    public static final String PASSWORD_GRANT_TYPE = "password";
    public static final String INTERNAL_GRANT_TYPE = "internal";
    public static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String EXT_ID_PARAMETER = "ext_id";
    public static final String AUTH_PROVIDER_PARAMETER = "auth_provider";
    public static final String GRANT_TYPE_PARAMETER = "grant_type";
    public static final String REFRESH_TOKEN_PARAMETER = "refresh_token";


    @Value("${oauth.clientId}")
    private String clientId;

    @Value("${oauth.clientSecret}")
    private String clientSecret;

    @Value("${oauth.accessTokenUri}")
    private String accessTokenUri;

    private RestOperations restOperations = new RestTemplate();


    @Override
    public OAuthTokenResponse authorizeWithInternalGrantType(String extId, String userId,
                                                             AuthProviderType authProvider) {
        return restOperations.postForEntity(accessTokenUri, formInternalRequest(extId, authProvider, userId),
                OAuthTokenResponse.class).getBody();
    }

    @Override
    public OAuthTokenResponse authorizeWithRefreshTokenGrantType(String refreshToken) {
        return restOperations.postForEntity(accessTokenUri, formRefreshTokenRequest(refreshToken),
                OAuthTokenResponse.class).getBody();
    }

    @Override
    public OAuthTokenResponse authorizeWithPasswordGrantType(String username, String password) {
        return restOperations.postForEntity(accessTokenUri, formPasswordGrantRequest(username, password),
                OAuthTokenResponse.class).getBody();

    }

    private HttpHeaders provideHeaders() {
        String clientCredentials = clientId + ":" + clientSecret;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        byte[] encoded;
        try {
            encoded = Base64.encode(clientCredentials.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + new String(encoded));
        return httpHeaders;
    }

    private HttpEntity<MultiValueMap<String, String>> formPasswordGrantRequest(String username, String password) {
        HttpHeaders httpHeaders = provideHeaders();

        val parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add(GRANT_TYPE_PARAMETER, PASSWORD_GRANT_TYPE);
        parametersMap.add(USERNAME_PARAMETER, username);
        parametersMap.add(PASSWORD_PARAMETER, password);

        return new HttpEntity<>(parametersMap, httpHeaders);
    }

    private HttpEntity<MultiValueMap<String, String>> formInternalRequest(String extId,
                                                                          AuthProviderType authProvider,
                                                                          String userId) {
        HttpHeaders httpHeaders = provideHeaders();

        val parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add(GRANT_TYPE_PARAMETER, INTERNAL_GRANT_TYPE);
        parametersMap.add(USERNAME_PARAMETER, userId);
        parametersMap.add(EXT_ID_PARAMETER, extId);
        parametersMap.add(AUTH_PROVIDER_PARAMETER, String.valueOf(authProvider));

        return new HttpEntity<>(parametersMap, httpHeaders);
    }

    private HttpEntity<MultiValueMap<String, String>> formRefreshTokenRequest(String refreshToken) {
        HttpHeaders httpHeaders = provideHeaders();

        val parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add(GRANT_TYPE_PARAMETER, REFRESH_TOKEN_GRANT_TYPE);
        parametersMap.add(REFRESH_TOKEN_PARAMETER, refreshToken);
        return new HttpEntity<>(parametersMap, httpHeaders);
    }

}
