package club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile;


import club.kuzyayo.stars.auth.api.dto.AuthProviderType;

public class GoogleProfile extends Profile {

    @Override
    public AuthProviderType getAuthProviderType() {
        return AuthProviderType.GOOGLE;
    }

}
