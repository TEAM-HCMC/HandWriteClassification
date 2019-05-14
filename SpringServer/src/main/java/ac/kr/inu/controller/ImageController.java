package ac.kr.inu.controller;

import ac.kr.inu.service.ImageService;
import ac.kr.inu.util.DirInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/save/train")
    public ResponseEntity<Boolean> saveTrainImage(@RequestPart(value = "image") final MultipartFile file, @ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());
        imageService.saveImage(file, accountId, DirInfo.TRAIN);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/save/compare")
    public ResponseEntity<Boolean> saveCompareImage(@RequestPart(value = "image") final MultipartFile file, @ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());
        imageService.saveImage(file, accountId, DirInfo.COMPARE);
        return ResponseEntity.ok(true);
    }

    @GetMapping("")
    public ResponseEntity<Boolean> getImageList() {
        //TODO
        //입력한 이미지 목록 가져오기
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteImage(String fileName, @ApiIgnore Authentication auth) {
        //TODO
        //입력한 이미지 삭제하는 기능 추가
        return ResponseEntity.ok(true);
    }

    //@RequestPart(value = "image") final MultipartFile file,
    //        String url = imageService.saveImage(file, accountId, DirInfo.TRAIN);
    //        Map result = imageService.contourImage(url);
    @PostMapping("/contour/train")
    public ResponseEntity<Map> contourTrainImage(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());

        Map result = imageService.contourImage(DirInfo.TRAIN, accountId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/contour/compare")
    public ResponseEntity<Map> contourCompareImage(@ApiIgnore Authentication auth) {
        Long accountId = Long.parseLong(auth.getPrincipal().toString());
        Map result = imageService.contourImage(DirInfo.COMPARE, accountId);

        return ResponseEntity.ok(result);
    }

}
