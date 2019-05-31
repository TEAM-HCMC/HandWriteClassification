package ac.kr.inu.dto.compare;

import org.junit.Test;

public class CompareResultResDtoTest {

    @Test
    public void 파싱() {
        String rate0 = "0.1";
        String rate1 ="1.0";
        String rate2 = "0.9453";
        String rate4 = "0.0";

        System.out.println(toInteger(subThree(replace(addPadding(rate0)))));
        System.out.println(toInteger(subThree(replace(addPadding(rate1)))));
        System.out.println(toInteger(subThree(replace(addPadding(rate2)))));
        System.out.println(toInteger(subThree(replace(addPadding(rate4)))));
    }

    private String addPadding(String rate){
        return rate+"0";
    }

    private String replace(String rate){
        return rate.replace(".","");
    }

    private String subThree(String rate){
        return rate.substring(0,3);
    }

    private Integer toInteger(String rate){
        return Integer.parseInt(rate);
    }
}