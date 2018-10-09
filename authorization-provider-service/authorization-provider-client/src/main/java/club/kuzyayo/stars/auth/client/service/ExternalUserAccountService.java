package club.kuzyayo.stars.auth.client.service;

import club.kuzyayo.stars.auth.api.dto.external.auth.provider.profile.Profile;

public interface ExternalUserAccountService {
    Long createAccountIfDoesNotExist(Profile externalAuthProviderUserProfile);
}
