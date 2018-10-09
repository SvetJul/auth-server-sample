package club.kuzyayo.stars.auth.provider.domain.entity;

import club.kuzyayo.stars.auth.api.dto.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AccountId implements Serializable {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProviderType authProviderType;

    @Column(nullable = false)
    private String extId;
}