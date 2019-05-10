package ac.kr.inu.domain;

import ac.kr.inu.security.context.AccountContext;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ACCOUNT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT_EMAIL", unique = true)
    @NotNull
    private String email;

    @Column(name = "ACCOUNT_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "ACCOUNT_NAME")
    @NotNull
    private String name;

    @Column(name = "ACCOUNT_ROLE")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    @Builder
    public Account(@NotNull String email, @NotNull String password, @NotNull String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountRole = AccountRole.USER;
    }

    public AccountContext toAccountContext() {
        return new AccountContext(this);
    }
}
