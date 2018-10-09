package club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import club.kuzyayo.stars.auth.api.dto.UserProfileDto;
import lombok.Data;

@Data
public abstract class Profile {

    private String extId;
    private String email;
    private String nickName;

    public abstract AuthProviderType getAuthProviderType();

    public UserProfileDto convertToDto() {
        return new UserProfileDto(extId, email, nickName, getAuthProviderType());
    }

}
