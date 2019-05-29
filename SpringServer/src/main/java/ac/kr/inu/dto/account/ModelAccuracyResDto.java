package ac.kr.inu.dto.account;

import lombok.Getter;

@Getter
public class ModelAccuracyResDto {

    private String accuracy;

    public ModelAccuracyResDto(String accuracy) {
        this.accuracy = getAccuracy(accuracy);
    }

    private String getAccuracy(String accuracy) {
        if(accuracy.equals("0")){
            return accuracy+"%";
        }
        return accuracy.substring(2, 4) + "%";
    }
}
