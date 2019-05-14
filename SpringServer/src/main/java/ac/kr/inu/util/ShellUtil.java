package ac.kr.inu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShellUtil {
    private static final String BASH = "/bin/bash";
    private static final String RUN = "-c";
    private static final String FAIL = "FAIL TO PRCESS.";
    private static final String NO_PARAMETER = "";
    private static final String BLANK = " ";

    public static Map execCommand(String... str) {
        Map<Integer, String> map = new HashMap<>();
        ProcessBuilder pb = new ProcessBuilder(str);

        pb.redirectErrorStream(true);
        Process process = null;

        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = null;
        if (process != null) {
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        List<String> echos = new ArrayList<>();
        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    echos.add(line);
                    stringBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (process != null) {
                process.waitFor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (process != null) {
            map.put(0, String.valueOf(process.exitValue()));

            for (int i = 1; i <= echos.size(); i++) {
                map.put(i, echos.get(i - 1));
            }
        }

        try {
            map.put(1, stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e) {
            if (stringBuilder.toString().length() == 0) {
                return map;
            }
        }

        return map;
    }

    public static String[] getBashCmd(final String shell) {
        return getBashCmd(shell, NO_PARAMETER);
    }

    public static String[] getBashCmd(final String shell, final String... parameter) {
        List<String> callCmd = getBasicCmd();
        StringBuilder command = new StringBuilder();
        command.append(shell)
                .append(BLANK);
        for (int i = 0; i < parameter.length; i++) {
            command.append(parameter[i])
                    .append(BLANK);
        }

        callCmd.add(command.toString());
        return callCmd.toArray(new String[0]);
    }

    private static List<String> getBasicCmd() {
        List<String> callCmd = new ArrayList<>();
        callCmd.add(BASH);
        callCmd.add(RUN);
        return callCmd;
    }

    public static Map getFailResult() {
        Map<Integer, String> result = new HashMap<>();
        result.put(0, FAIL);
        return result;
    }
}
