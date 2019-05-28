package ac.kr.inu.dto.account;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.Model;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AccountSaveReqDto {
    private String email;
    private String password;
    private String name;

    public Account toAccountEntity(PasswordEncoder passwordEncoder, Model model) {
        return Account.builder()
                .email(this.email)
                .model(model)
                .password(passwordEncoder.encode(this.password))
                .build();
    }

    public Model toModelEntity() {
        return new Model(this);
    }
}
