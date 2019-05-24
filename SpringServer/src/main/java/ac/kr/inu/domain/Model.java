package ac.kr.inu.domain;

import ac.kr.inu.dto.account.AccountSaveReqDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MODEL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Model {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MODEL_ID")
    private Long id;

    @Column(name = "MODEL_NAME", unique = true)
    @NotNull
    private String name;

    public Model(AccountSaveReqDto accountSaveReqDto) {
        this.name = accountSaveReqDto.getName();
    }
}
