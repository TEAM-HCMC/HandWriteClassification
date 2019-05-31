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
        this.wrongRate = "Wrong Rate : " + getRate(map.get(1).toString()) + "%";
        this.correctRate = "Correct Rate : " + getRate(map.get(2).toString()) + "%";
    }

    private String getRate(String rate) {
        String parseRate = rate.split(":")[1];
        return String.valueOf(Integer.parseInt(subThree(replace(addPadding(parseRate)))));
    }

    private String addPadding(String rate) {
        return rate + "0";
    }

    private String replace(String rate) {
        return rate.replace(".", "");
    }

    private String subThree(String rate) {
        return rate.substring(0, 3);
    }

    private void validateMap(Map map) {
        if (map == null) {
            throw new NoResultException();
        }
    }
}
