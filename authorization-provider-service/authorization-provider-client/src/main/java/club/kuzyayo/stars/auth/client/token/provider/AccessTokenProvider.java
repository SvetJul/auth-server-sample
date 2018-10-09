package club.kuzyayo.stars.auth.client.token.provider;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;

public interface AccessTokenProvider {

    OAuthTokenResponse authorizeWithInternalGrantType(String extId, String userId,
                                                      AuthProviderType authProvider);

    OAuthTokenResponse authorizeWithRefreshTokenGrantType(String refreshToken);

    OAuthTokenResponse authorizeWithPasswordGrantType(String username, String password);

}
