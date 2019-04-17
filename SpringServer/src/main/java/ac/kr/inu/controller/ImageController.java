package ac.kr.inu.controller;

import ac.kr.inu.service.ImageService;
import ac.kr.inu.util.FileNameUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/contour")
    public ResponseEntity<Map> contourImage(@RequestPart(value = "image") final MultipartFile file) {

        String url = imageService.saveImage(file);

        Map result = imageService.contourImage(FileNameUtil.getSourceNameWithOutExt(url));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/learn")
    public ResponseEntity<Map> learnModel(String name) {
        Map result = imageService.learnModel(name);
        return ResponseEntity.ok(result);
    }


}
