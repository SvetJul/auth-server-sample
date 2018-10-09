package club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

public class ProfileExtractor {

    private final Class<? extends Profile> clazz;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Map<AuthProviderType, Class<? extends Profile>> authProviders = new HashMap<>();

    static {
        authProviders.put(AuthProviderType.FACEBOOK, FacebookProfile.class);
        authProviders.put(AuthProviderType.GOOGLE, GoogleProfile.class);
    }


    public ProfileExtractor(AuthProviderType providerType) {
        this.clazz = authProviders.get(providerType);
    }

    public Profile extract(Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            Object details = ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            try {
                byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(details);
                return OBJECT_MAPPER.readValue(bytes, clazz);
            } catch (Exception e) {
                throw new RuntimeException("Extracting external authentication profile failed", e);
            }
        } else {
            throw new RuntimeException("principal is not an instance of OAuth2Authentication");
        }

    }

}
