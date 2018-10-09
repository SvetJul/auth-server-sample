package club.kuzyayo.stars.auth.provider.rest;

import club.kuzyayo.stars.auth.api.dto.PrincipalDto;
import club.kuzyayo.stars.auth.api.dto.Role;
import club.kuzyayo.stars.auth.api.dto.UserProfileDto;
import club.kuzyayo.stars.auth.provider.domain.entity.AccountId;
import club.kuzyayo.stars.auth.provider.domain.entity.CGUser;
import club.kuzyayo.stars.auth.provider.domain.entity.UserAccount;
import club.kuzyayo.stars.auth.provider.domain.repository.CGUserRepository;
import club.kuzyayo.stars.auth.provider.domain.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static club.kuzyayo.stars.auth.api.dto.RestApi.SAVE_EXTERNAL_USER_RELATIVE_PATH;
import static club.kuzyayo.stars.auth.api.dto.RestApi.USER_INFO_BASE_URL;

@RestController
@RequestMapping(USER_INFO_BASE_URL)
@AllArgsConstructor
public class UserInfoController {

    private final CGUserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @GetMapping
    public ResponseEntity<PrincipalDto> user(Principal principal) {
        PrincipalDto userDto = (PrincipalDto) ((OAuth2Authentication) principal).getUserAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(SAVE_EXTERNAL_USER_RELATIVE_PATH)
    public ResponseEntity<Long> saveOnDemand(@RequestBody UserProfileDto profile) {
        Optional<String> email = Optional.ofNullable(profile.getEmail());
        AccountId accountId = new AccountId(profile.getProviderType(), profile.getExtId());
        UserAccount userAccount = userAccountRepository.findOne(accountId);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount.getUser().getId());
        } else {
            UserAccount newAccount = UserAccount.builder()
                    .accountId(accountId)
                    .nickName(profile.getNickName())
                    .build();
            if (email.isPresent()) {
                Optional<CGUser> user = Optional.ofNullable(userRepository.findByEmail(email.get()));
                if (user.isPresent()) {
                    newAccount.setUser(user.get());
                    UserAccount entity = userAccountRepository.save(newAccount);
                    return ResponseEntity.ok(entity.getUser().getId());
                } else {
                    Long userId = createUserAndAccount(profile, newAccount);
                    return ResponseEntity.ok(userId);
                }
            } else {
                Long userId = createUserAndAccount(profile, newAccount);
                return ResponseEntity.ok(userId);
            }
        }
    }

    private Long createUserAndAccount(UserProfileDto profile, UserAccount newAccount) {
        CGUser newUser = CGUser.builder()
                .email(profile.getEmail())
                .nickName(newAccount.getNickName())
                .roles(Collections.singleton(Role.USER))
                .accounts(Collections.singleton(newAccount))
                .build();
        newAccount.setUser(newUser);

        UserAccount entity = userAccountRepository.save(newAccount);
        return entity.getUser().getId();
    }

}
