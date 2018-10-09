package club.kuzyayo.stars.auth.client.service;

import club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile.Profile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import static club.kuzyayo.stars.auth.api.dto.RestApi.SAVE_EXTERNAL_USER_PATH;

@Service
public class ExternalUserAccountServiceImpl implements ExternalUserAccountService {

    private final RestOperations restOperations;
    private final String authServerUrl;

    public ExternalUserAccountServiceImpl(@Qualifier("systemRestOperations") RestOperations restOperations,
                                          @Value("${authorization-provider.service.url}") String authServerUrl) {
        this.restOperations = restOperations;
        this.authServerUrl = authServerUrl;
    }

    @Override
    public Long createAccountIfDoesNotExist(Profile externalAuthProviderUserProfile) {
        return restOperations.postForEntity(authServerUrl + SAVE_EXTERNAL_USER_PATH,
                externalAuthProviderUserProfile.convertToDto(),
                Long.class)
                .getBody();
    }

}
