package ac.kr.inu.dto.account;

import ac.kr.inu.domain.Account;
import lombok.Getter;

@Getter
public class AccountInfoResDto {
    private String email;
    private String name;
    private LogResDto log;
    private ModelAccuracyResDto model;

    public AccountInfoResDto(Account account, LogResDto log, ModelAccuracyResDto accuracyResDto) {
        this.email = account.getEmail();
        this.name = account.getModelName();
        this.log = log;
        this.model = accuracyResDto;
    }
}
