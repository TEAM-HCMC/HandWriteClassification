package ac.kr.inu.dto.jwt;

import lombok.Getter;

@Getter
public class JwtResDto {
    private String jwt;

    public JwtResDto(String jwt) {
        this.jwt = jwt;
    }
}
