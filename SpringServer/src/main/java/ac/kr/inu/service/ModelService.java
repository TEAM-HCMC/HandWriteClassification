package ac.kr.inu.service;

import ac.kr.inu.util.ShellUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@NoArgsConstructor
public class ModelService {

    private final String TRAIN = "train/";
    private final String COMPARE = "compare/";

    private static Logger logger = LoggerFactory.getLogger(ModelService.class);

    public Map learnModel(String name) {
        log.info("input name : " + name);
        String[] callCmd = ShellUtil.getBashCmd("sh ../script/train.sh ", TRAIN + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    public Map compareModel(String name) {
        String[] callCmd = ShellUtil.getBashCmd("sh ../script/compare.sh ", COMPARE + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }
}
