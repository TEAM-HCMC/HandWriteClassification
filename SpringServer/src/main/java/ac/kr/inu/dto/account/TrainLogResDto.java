package ac.kr.inu.dto.account;

import ac.kr.inu.util.FileReadUtils;
import ac.kr.inu.util.LogFlag;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainLogResDto {

    private String created;
    private String flag;

    public TrainLogResDto(File file) {
        List<String> logs = getLogs(file);
        this.created = getCreated(logs);
        this.flag = getFlag(logs);
    }

    private List<String> getLogs(File file) {
        List<String> logs = new ArrayList<>();
        FileReadUtils.addLogs(logs, file);
        return logs;
    }

    private String getCreated(List<String> logs) {
        int lastIdx = logs.size();
        return logs.get(lastIdx - 2);
    }

    private String getFlag(List<String> logs) {
        int lastIdx = logs.size();
        int flag = Integer.valueOf(logs.get(lastIdx - 1).split("=")[1]);
        return LogFlag.findMessageByFlag(flag);
    }
}
