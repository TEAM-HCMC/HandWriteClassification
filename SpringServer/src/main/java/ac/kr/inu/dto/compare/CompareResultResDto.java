package ac.kr.inu.dto.compare;

import ac.kr.inu.exception.NoResultException;
import lombok.Getter;

import java.util.Map;

@Getter
public class CompareResultResDto {
    private String wrongRate;
    private String correctRate;


    public CompareResultResDto(Map map) {
        validateMap(map);
        this.wrongRate = map.get(1).toString();
        this.correctRate = map.get(2).toString();
    }

    private void validateMap(Map map) {
        if (map == null) {
            throw new NoResultException();
        }
    }
}
