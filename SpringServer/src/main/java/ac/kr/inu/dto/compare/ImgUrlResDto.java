package ac.kr.inu.dto.compare;

import ac.kr.inu.domain.ComparedImg;
import lombok.Getter;

@Getter
public class ImgUrlResDto {
    private String url;
    private String accuracy;

    public ImgUrlResDto(ComparedImg comparedImg) {
        this.url = comparedImg.getImgUrl();
        this.accuracy = comparedImg.getPercentage()+"%";
    }
}
