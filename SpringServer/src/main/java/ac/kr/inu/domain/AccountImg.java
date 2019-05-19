package ac.kr.inu.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_IMG")
@Getter
@NoArgsConstructor
public class AccountImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_IMG_ID")
    private Long id;

    @Column(name = "ORIGINAL_NAME")
    private String originalName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;


    public AccountImg(Account account, String originalName) {
        this.account = account;
        this.originalName = originalName;
    }

}
