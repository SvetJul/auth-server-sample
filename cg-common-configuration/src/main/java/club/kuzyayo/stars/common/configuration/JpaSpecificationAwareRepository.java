package club.kuzyayo.stars.common.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface JpaSpecificationAwareRepository<E, ID extends Serializable> extends JpaRepository<E, ID>,
        JpaSpecificationExecutor<E> {
}
