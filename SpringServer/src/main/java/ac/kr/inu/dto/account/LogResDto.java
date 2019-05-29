package ac.kr.inu.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogResDto {

    private TrainLogResDto train;
    private TrainContourResDto trainContour;
    private CompareContourResDto compareContour;
    private CompareResDto compare;

    @Builder
    public LogResDto(TrainLogResDto train, TrainContourResDto trainContour, CompareContourResDto compareContour, CompareResDto compare) {
        this.train = train;
        this.trainContour = trainContour;
        this.compareContour = compareContour;
        this.compare = compare;
    }
}
