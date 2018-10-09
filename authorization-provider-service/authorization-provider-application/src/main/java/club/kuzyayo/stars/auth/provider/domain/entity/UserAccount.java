package club.kuzyayo.stars.auth.provider.domain.entity;

import lombok.*;

import javax.persistence.*;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_accounts", schema = "cgusersm")
public class UserAccount {

    @EmbeddedId
    private AccountId accountId;

    @Column
    private String nickName;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private CGUser user;

}