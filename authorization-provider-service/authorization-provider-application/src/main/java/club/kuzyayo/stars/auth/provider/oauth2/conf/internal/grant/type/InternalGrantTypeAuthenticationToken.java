package club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class InternalGrantTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public InternalGrantTypeAuthenticationToken(Long userId, Credentials credentials) {
        super(userId, credentials);
    }

    public InternalGrantTypeAuthenticationToken(Long userId, Credentials credentials, Collection<? extends GrantedAuthority> authorities) {
        super(userId, credentials, authorities);
    }

}
