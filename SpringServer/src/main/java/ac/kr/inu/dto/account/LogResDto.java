package ac.kr.inu.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogResDto {

    private TrainLogResDto train;
    private ContourLogResDto contour;
    private CompareResDto compare;

    @Builder
    public LogResDto(TrainLogResDto train, ContourLogResDto contour, CompareResDto compare) {
        this.train = train;
        this.contour = contour;
        this.compare=compare;
    }
}
