package ac.kr.inu.dto.compare;

import ac.kr.inu.domain.ComparedImg;
import lombok.Getter;

@Getter
public class ImgUrlResDto {
    private String url;

    public ImgUrlResDto(ComparedImg comparedImg) {
        this.url = comparedImg.getImgUrl();
    }
}
