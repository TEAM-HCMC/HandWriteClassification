package ac.kr.inu.controller;

import ac.kr.inu.dto.model.ModelReqDto;
import ac.kr.inu.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * @param modelReqDto : 이니셜
     * @return
     */
    @PostMapping("/learn")
    public ResponseEntity<Map> learnModel(@RequestBody final ModelReqDto modelReqDto) {
        Map result = modelService.learnModel(modelReqDto.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/compare")
    public ResponseEntity<Map> compare(@RequestBody final ModelReqDto modelReqDto) {
        Map result = modelService.compareModel(modelReqDto.getName());
        return ResponseEntity.ok(result);
    }

}
