package club.kuzyayo.stars.auth.provider.oauth2.conf.internal.grant.type;

import club.kuzyayo.stars.auth.provider.oauth2.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@Slf4j
public class InternalGrantTypeAuthenticationProvider extends DaoAuthenticationProvider {

    public InternalGrantTypeAuthenticationProvider() {

    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Assert.isInstanceOf(InternalGrantTypeAuthenticationToken.class, authentication,
                "Only InternalGrantTypeAuthenticationToken is supported");

        Assert.isInstanceOf(CustomUserDetails.class, userDetails,
                "Only CustomUserDetails is supported");

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        InternalGrantTypeAuthenticationToken internalAuth = (InternalGrantTypeAuthenticationToken) authentication;
        Credentials credentials = (Credentials) internalAuth.getCredentials();
        boolean correctCredentials = customUserDetails.getUserAccounts().stream().filter(
                userAccount -> userAccount.getAccountId().getAuthProviderType().equals(credentials.getAuthProvider()) &&
                        userAccount.getAccountId().getExtId().equals(credentials.getExtId()))
                .count() == 1;
        if (!correctCredentials) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (InternalGrantTypeAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
