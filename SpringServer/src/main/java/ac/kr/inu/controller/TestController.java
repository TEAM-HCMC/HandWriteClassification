package ac.kr.inu.controller;
//hello chanin
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/test")
public class TestController {

    @PostMapping("/multipart")
    public ResponseEntity<MultipartFile> testMultipart(@RequestPart(value = "image") MultipartFile multipartFile) {

        if (multipartFile == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(multipartFile);
    }

    @PostMapping("/multipart/list")
    public ResponseEntity<List<MultipartFile>> testListMultipart(@RequestPart(value = "images") List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(multipartFiles);
    }
}
