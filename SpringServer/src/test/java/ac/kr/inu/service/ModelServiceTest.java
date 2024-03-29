package ac.kr.inu.service;

import ac.kr.inu.util.DirInfo;
import org.junit.Test;

import java.io.File;

public class ModelServiceTest {

    @Test
    public void 파일찾기_테스트() {
        String outputPath = DirInfo.OUTPUT;
        String outputImageReg = "pci" + "([0-9]*_[0-9]*)\\..*";
        File dirFile = new File(outputPath);

        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.getName().matches(outputImageReg)){
                System.out.println(file.getName());
                if(file.getName().split("_")[1].substring(0, 1).equals("0")){
                    System.out.println(file.getName().split("_")[1].substring(0, 1));
                }else{
                    System.out.println(file.getName().split("_")[1].substring(0, 2));
                }
            }
        }
    }
}