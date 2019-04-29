package ac.kr.inu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ShellUtill {
    private static final String BASH = "/bin/bash";
    private static final String RUN = "-c";

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

        try {
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
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
        return getBashCmd(shell, null);
    }

    public static String[] getBashCmd(final String shell, final String parameter) {
        List<String> callCmd = getBasicCmd();
        callCmd.add(shell);
        if (parameter == null) {
            return (String[]) callCmd.toArray();
        }
        callCmd.add(parameter);
        return (String[]) callCmd.toArray();
    }

    private static List<String> getBasicCmd() {
        List<String> callCmd = new ArrayList<>();
        callCmd.add(BASH);
        callCmd.add(RUN);
        return callCmd;
    }
}
