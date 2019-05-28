package ac.kr.inu.dto.account;

import lombok.Getter;

@Getter
public class LogResDto {
    private TrainLogResDto train;
    private ContourLogResDto contour;

    public LogResDto(TrainLogResDto train, ContourLogResDto contour) {
        this.train = train;
        this.contour = contour;
    }
}
