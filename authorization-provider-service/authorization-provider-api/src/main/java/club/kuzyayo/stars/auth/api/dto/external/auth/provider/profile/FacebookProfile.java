package club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile;


import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookProfile extends Profile {
    @Override
    @JsonProperty("id")
    public String getExtId() {
        return super.getExtId();
    }


    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @JsonProperty("name")
    public String getNickName() {
        return super.getNickName();
    }

    @Override
    public AuthProviderType getAuthProviderType() {
        return AuthProviderType.FACEBOOK;
    }
}
