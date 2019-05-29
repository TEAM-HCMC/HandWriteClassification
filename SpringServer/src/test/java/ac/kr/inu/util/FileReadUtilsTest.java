package ac.kr.inu.util;

import ac.kr.inu.dto.account.CompareContourResDto;
import ac.kr.inu.dto.account.CompareResDto;
import ac.kr.inu.dto.account.TrainContourResDto;
import ac.kr.inu.dto.account.TrainLogResDto;
import org.junit.Test;

import java.io.File;

public class FileReadUtilsTest {

    private static final String TRAIN_CONTOUR = "train_contour";
    private static final String COMPARE_CONTOUR = "compare_contour";
    private static final String TRAIN = "train";
    private static final String COMPARE = "compare";

    @Test
    public void 로그읽기() {
        String LogDir = DirInfo.LOG + "pci";
        File[] files = new File(LogDir).listFiles();
        FileReadUtils fileReadUtils = new FileReadUtils();

        TrainLogResDto trainLogResDto = new TrainLogResDto(fileReadUtils.findCategoryLogFile(files, TRAIN));
        TrainContourResDto trainContourResDto = new TrainContourResDto(fileReadUtils.findCategoryLogFile(files, TRAIN_CONTOUR));
        CompareContourResDto compareContourResDto = new CompareContourResDto(fileReadUtils.findCategoryLogFile(files, COMPARE_CONTOUR));
        CompareResDto compareResDto = new CompareResDto(fileReadUtils.findCategoryLogFile(files, COMPARE));

        System.out.println(trainLogResDto.getCreated());
    }
}