package club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Credentials implements Cloneable{

    private String extId;
    private AuthProviderType authProvider;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Credentials(extId, authProvider);
    }
}
