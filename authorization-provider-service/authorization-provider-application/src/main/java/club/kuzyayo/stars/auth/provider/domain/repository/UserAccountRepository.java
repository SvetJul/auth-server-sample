package club.kuzyayo.stars.auth.provider.domain.repository;

import club.kuzyayo.stars.auth.provider.domain.entity.AccountId;
import club.kuzyayo.stars.auth.provider.domain.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, AccountId> {
}
