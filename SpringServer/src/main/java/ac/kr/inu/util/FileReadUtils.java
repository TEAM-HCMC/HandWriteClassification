package ac.kr.inu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileReadUtils {

    private static final String ANY_WORDS = ".*";
    private static final String DEFAULT_LOG = "default.txt";

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
}
