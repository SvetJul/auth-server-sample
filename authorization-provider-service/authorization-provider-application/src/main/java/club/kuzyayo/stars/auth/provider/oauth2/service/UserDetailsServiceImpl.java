package club.kuzyayo.stars.auth.provider.oauth2.service;

import club.kuzyayo.stars.auth.provider.domain.entity.CGUser;
import club.kuzyayo.stars.auth.provider.domain.repository.CGUserRepository;
import club.kuzyayo.stars.auth.provider.oauth2.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CGUserRepository userRepository;

    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
       CGUser user = userRepository.findOne(Long.valueOf(id));
        if (user == null || user.getRoles().size() == 0) {
            throw new UsernameNotFoundException("CGUser with id '" + id + "' hasn't been found");
        }
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getNickName(), user.getPassword(), user.getRoles(),
                user.getAccounts());
    }
}
