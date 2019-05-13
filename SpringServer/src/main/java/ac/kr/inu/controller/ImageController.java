package ac.kr.inu.controller;

import ac.kr.inu.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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
    public ResponseEntity<Boolean> saveImgTemporary(@RequestPart(value = "") final MultipartFile multipartFile) {

        return ResponseEntity.ok(true);
    }

    @PostMapping("/contour/train")
    public ResponseEntity<Map> contourTrainImage(@RequestPart(value = "image") final MultipartFile file, @ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        String url = imageService.saveImage(file, accountId, TRAIN);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/contour/compare")
    public ResponseEntity<Map> contourCompareImage(@RequestPart(value = "image") final MultipartFile file, @ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        String url = imageService.saveImage(file, accountId, COMPARE);

        Map result = imageService.contourImage(url);

        return ResponseEntity.ok(result);
    }

}
