package club.kuzyayo.stars.auth.provider.domain.entity;

import club.kuzyayo.stars.auth.api.dto.Role;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users", schema = "cgusersm")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CGUser implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User")
    @SequenceGenerator(name = "User", sequenceName = "cgusersm.user_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",schema = "cgusersm",
            joinColumns = {@JoinColumn(name = "user_id")})
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @NotEmpty
    @OneToMany(mappedBy = "user")
    private Set<UserAccount> accounts;

    @Column
    private String password;

}
