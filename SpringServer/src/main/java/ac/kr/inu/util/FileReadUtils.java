package ac.kr.inu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileReadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileReadUtils.class);

    private static final String ANY_WORDS = ".*";
    private static final String DEFAULT_LOG = "default.txt";
    private static final String DEFAULT_MODEL_ACCURACY = "0";

    public static void addLogs(List<String> logs, File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File findCategoryLogFile(File[] files, String category) {
        return Arrays.stream(files)
                .filter(file -> isCategory(file, category))
                .findFirst()
                .orElseGet(this::getDefaultLogFile);
    }

    private boolean isCategory(File file, String category) {
        return file.getName()
                .matches(getRegex(category));
    }

    private String getRegex(String category) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANY_WORDS)
                .append(category)
                .append(ANY_WORDS);
        return sb.toString();
    }

    private File getDefaultLogFile() {
        return new File(DirInfo.LOG + DEFAULT_LOG);
    }

    public File getAccuracyFile(String modelName) {
        return new File(DirInfo.OUTPUT + modelName + "_accuracy.txt");
    }

    public String getAccuracy(File file) {
        String accuracy="0:0";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                accuracy = line;
            }
        } catch (IOException e) {
            log.info("학습된 모델 없음.");
            return DEFAULT_MODEL_ACCURACY;
        }

        return parsingAccuracy(accuracy);
    }

    private String parsingAccuracy(String accuracy) {
        return accuracy.replace(" ", "").split(":")[1];
    }
}
