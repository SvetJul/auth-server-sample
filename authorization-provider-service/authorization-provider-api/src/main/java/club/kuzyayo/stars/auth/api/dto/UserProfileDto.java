package club.kuzyayo.stars.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserProfileDto {

    private String extId;
    private String email;
    private String nickName;
    private AuthProviderType providerType;

}
