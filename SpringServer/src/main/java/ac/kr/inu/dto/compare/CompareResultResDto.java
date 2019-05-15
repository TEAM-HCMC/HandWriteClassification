package ac.kr.inu.dto.compare;

import lombok.Getter;

import java.util.Map;

@Getter
public class CompareResultResDto {
    private String wrongRate;
    private String correctRate;


    public CompareResultResDto(Map map) {
        this.wrongRate = map.get(1).toString();
        this.correctRate = map.get(2).toString();
    }
}
