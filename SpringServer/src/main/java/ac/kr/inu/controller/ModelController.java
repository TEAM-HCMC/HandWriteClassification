package ac.kr.inu.controller;

import ac.kr.inu.dto.compare.CompareResultResDto;
import ac.kr.inu.service.ModelService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/train")
    public ResponseEntity<Map> trainModel(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        Map result = modelService.trainModel(accountId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/compare")
    public ResponseEntity<Map> compareImage(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        Map result = modelService.compareModel(accountId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/train")
    public ResponseEntity<String> watchLearning(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());
        Map result = modelService.watchTraining(accountId);

        return ResponseEntity.ok("");
    }

    @GetMapping("/compare")
    public ResponseEntity<CompareResultResDto> getCompareResult(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        return ResponseEntity.ok(modelService.getCompareResult(accountId));
    }
}
