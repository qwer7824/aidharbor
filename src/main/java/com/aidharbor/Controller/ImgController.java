package com.aidharbor.Controller;

import com.aidharbor.DTO.Image.DisplayedImageDTO;
import com.aidharbor.Service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImgController {

    private final ImgService imgService;

    /**
     * 글(Content) 등록 이전에 이미지를 추가했을 시 S3에 이미지 파일을 저장한다.
     */
    @PostMapping("/board/displayedImage")
    public ResponseEntity<DisplayedImageDTO> saveTempImg(MultipartFile img) {
        if(img == null || img.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        DisplayedImageDTO imgDTO = null;
        try {
            imgDTO = imgService.saveTemporaryImage(img);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(imgDTO, HttpStatus.OK);
    }
}
