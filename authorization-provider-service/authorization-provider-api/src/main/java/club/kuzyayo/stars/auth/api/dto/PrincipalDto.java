package club.kuzyayo.stars.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrincipalDto implements Principal {

    /**
     * User ID
     */
    private String username;
    private String email;
    private String nickName;

    @Override
    public String toString() {
        return username;
    }

    public String getName() {
        return username;
    }
}
