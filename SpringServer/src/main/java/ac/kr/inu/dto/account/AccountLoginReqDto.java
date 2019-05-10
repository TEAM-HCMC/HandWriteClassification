package ac.kr.inu.dto.account;

import ac.kr.inu.security.token.PreAuthorizationToken;
import lombok.Getter;

@Getter
public class AccountLoginReqDto {
    private String email;
    private String password;

    public PreAuthorizationToken toPreAuthorizationToken() {
        return new PreAuthorizationToken(this.email, this.password);
    }
}
