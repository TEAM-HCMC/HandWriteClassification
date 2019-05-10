package ac.kr.inu.controller;

import ac.kr.inu.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<Boolean> saveImgTemporary(@RequestPart(value = "") final MultipartFile multipartFile){


        return ResponseEntity.ok(true);
    }

    @CrossOrigin("*")
    @PostMapping("/contour/train")
    public ResponseEntity<Map> contourTrainImage(@RequestPart(value = "image") final MultipartFile file, final String name) {

        String url = imageService.saveImage(file, name, TRAIN);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

    @CrossOrigin("*")
    @PostMapping("/contour/compare")
    public ResponseEntity<Map> contourCompareImage(@RequestPart(value = "image") final MultipartFile file, final String name) {

        String url = imageService.saveImage(file, name, COMPARE);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

}
