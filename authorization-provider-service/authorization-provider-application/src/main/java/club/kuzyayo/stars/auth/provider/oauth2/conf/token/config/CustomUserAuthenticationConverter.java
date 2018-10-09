package club.kuzyayo.stars.auth.provider.oauth2.conf.token.config;

import club.kuzyayo.stars.auth.provider.oauth2.CustomUserDetails;
import club.kuzyayo.stars.starter.config.security.CustomAuthenticationExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Didn't want to put CustomUserDetails in common module, that's this method in separate class
 */
public class CustomUserAuthenticationConverter extends CustomAuthenticationExtractor {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (!CollectionUtils.isEmpty(authentication.getAuthorities())) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("UserDetails is instance of " + principal.getClass() + " rather than CustomUserDetails.");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        response.put(EMAIL, userDetails.getEmail());
        response.put(NICK_NAME, userDetails.getNickName());
        return response;
    }

}
