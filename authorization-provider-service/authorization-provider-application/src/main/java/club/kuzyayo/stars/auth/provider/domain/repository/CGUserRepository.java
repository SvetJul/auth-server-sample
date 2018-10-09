package club.kuzyayo.stars.auth.provider.domain.repository;

import club.kuzyayo.stars.auth.provider.domain.entity.CGUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CGUserRepository extends CrudRepository<CGUser, Long> {

    @Override
    @Query("select u from CGUser u left join fetch u.roles left join fetch u.accounts where u.id = :id")
    CGUser findOne(@Param("id") Long id);

    CGUser findByEmail(String email);
}
