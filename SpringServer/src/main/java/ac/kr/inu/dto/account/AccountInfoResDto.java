package ac.kr.inu.dto.account;

import ac.kr.inu.domain.Account;
import lombok.Getter;

@Getter
public class AccountInfoResDto {
    private String email;
    private String name;

    public AccountInfoResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
    }
}
