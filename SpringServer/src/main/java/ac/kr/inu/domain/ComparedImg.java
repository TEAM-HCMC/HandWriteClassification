package ac.kr.inu.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COMPARED_IMG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComparedImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPARED_IMG_ID")
    private Long id;

    @Column(name = "COMPARED_IMG_URL")
    private String imgUrl;

    @Column(name = "PERCENTAGE")
    private String percentage;

    @Builder
    public ComparedImg(String imgUrl,String percentage, Account account) {
        this.imgUrl = imgUrl;
        this.percentage=percentage;
        this.account = account;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
