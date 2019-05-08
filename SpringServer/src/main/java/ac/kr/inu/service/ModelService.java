package ac.kr.inu.service;

import ac.kr.inu.util.ShellUtill;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@NoArgsConstructor
public class ModelService {

    private final String TRAIN = "train/";
    private final String COMPARE = "compare/";

    private static Logger logger = LoggerFactory.getLogger(ModelService.class);

    public Map learnModel(String name) {
        String[] callCmd = ShellUtill.getBashCmd("sh ../script/train.sh ", TRAIN + name);
        Map map = ShellUtill.execCommand(callCmd);
        return map;
    }

    public Map compareModel(String name) {
        String[] callCmd = ShellUtill.getBashCmd("sh ../script/compare.sh ", COMPARE + name);
        Map map = ShellUtill.execCommand(callCmd);
        return map;
    }
}
