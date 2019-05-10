package ac.kr.inu.controller;

import ac.kr.inu.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * @param name : 이니셜
     * @return
     */
    @CrossOrigin("*")
    @PostMapping("/learn")
    public ResponseEntity<Map> learnModel(final String name) {
        Map result = modelService.learnModel(name);
        return ResponseEntity.ok(result);
    }

    @CrossOrigin("*")
    @GetMapping("/compare")
    public ResponseEntity<Map> compare(final String name) {
        Map result = modelService.compareModel(name);
        return ResponseEntity.ok(result);
    }

}
