package ac.kr.inu.dto.account;

import ac.kr.inu.domain.Account;
import lombok.Getter;

@Getter
public class AccountInfoResDto {
    private String email;
    private String name;
    private LogResDto log;

    public AccountInfoResDto(Account account, LogResDto log) {
        this.email = account.getEmail();
        this.name = account.getModelName();
        this.log = log;
    }
}
