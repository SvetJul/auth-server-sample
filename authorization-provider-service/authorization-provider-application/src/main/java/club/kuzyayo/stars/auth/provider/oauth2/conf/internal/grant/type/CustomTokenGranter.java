package club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;


public class CustomTokenGranter extends AbstractTokenGranter {

    public static final String GRANT_TYPE = "internal";
    public static final String USERNAME = "username";
    public static final String EXT_ID = "ext_id";
    public static final String AUTH_PROVIDER = "auth_provider";
    private final AuthenticationManager authenticationManager;


    public CustomTokenGranter(AuthorizationServerTokenServices tokenServices,
                              ClientDetailsService clientDetailsService,
                              OAuth2RequestFactory requestFactory,
                              AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;

    }

    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        Long userId = Long.valueOf(parameters.get(USERNAME));
        String extId = parameters.get(EXT_ID);
        AuthProviderType authProvider = AuthProviderType.valueOf(parameters.get(AUTH_PROVIDER));

        Authentication userAuth = new InternalGrantTypeAuthenticationToken(userId, new Credentials(extId, authProvider));
        ((InternalGrantTypeAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + userId);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}