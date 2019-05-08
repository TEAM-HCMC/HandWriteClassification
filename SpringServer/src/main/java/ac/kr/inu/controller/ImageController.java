package ac.kr.inu.controller;

import ac.kr.inu.service.ImageService;
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

    private static final String TRAIN = "train/";
    private static final String COMPARE = "compare/";

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/contour/train")
    public ResponseEntity<Map> contourTrainImage(@RequestPart(value = "image") final MultipartFile file, final String name) {

        String url = imageService.saveImage(file, name, TRAIN);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/contour/compare")
    public ResponseEntity<Map> contourCompareImage(@RequestPart(value = "image") final MultipartFile file, final String name) {

        String url = imageService.saveImage(file, name, COMPARE);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

}
