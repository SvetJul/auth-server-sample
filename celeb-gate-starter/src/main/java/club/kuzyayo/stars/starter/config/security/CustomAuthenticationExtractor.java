package club.kuzyayo.stars.starter.config.security;

import club.kuzyayo.stars.auth.api.dto.PrincipalDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class CustomAuthenticationExtractor extends DefaultUserAuthenticationConverter {

    protected static final String EMAIL = "email";
    protected static final String NICK_NAME = "nick_name";

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(UserAuthenticationConverter.USERNAME)) {
            String userId = (String) map.get(UserAuthenticationConverter.USERNAME);
            String email = (String) map.get(EMAIL);
            String nickName = (String) map.get(NICK_NAME);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(new PrincipalDto(
                    userId,
                    email,
                    nickName),
                    "N/A", authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(UserAuthenticationConverter.AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

}
