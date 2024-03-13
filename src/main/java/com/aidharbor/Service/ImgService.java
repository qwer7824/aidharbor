package com.aidharbor.Service;

import com.aidharbor.DTO.Image.DisplayedImageDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImgService {

    private final S3Uploader s3Uploader;

    /** 이미지 임시저장 폴더에 저장 */
    @Value("${Locations.IMAGE_DIR}")
    private String IMAGE_DIR;

    @Value("${Locations.IMAGE_DIR2}")
    private String IMAGE_DIR2;

    private String IMAGE = "/images/";

    /** 이미지 임시저장 폴더에 저장 */
    public DisplayedImageDTO saveTemporaryImage(MultipartFile ImgFile) throws IOException {
        String oriImgName = ImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (StringUtils.isEmpty(oriImgName)) {
            throw new IllegalArgumentException();
        }

        BufferedImage bufferedImage = ImageIO.read(ImgFile.getInputStream());

        imgName = oriImgName;

        // 파일 객체 생성
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(IMAGE_DIR + imgName);
        imgUrl = IMAGE + imgName;

        ImgFile.transferTo(file); // 파일 저장

        return DisplayedImageDTO.builder()
                .originalName(oriImgName)
                .savedPath(imgUrl)
                .build();
    }

    public String convert(ProductDTO productDTO) throws IOException {
        String content = productDTO.getContent();
        Document doc = Jsoup.parse(content);

        Element body = doc.body(); // body 요소 가져오기

        for (Element img : body.select("img")) {
            String src = IMAGE_DIR2 + img.attr("src");
            File file = new File(src);
            String s3Url = s3Uploader.uploadFile(file, "images");
            img.attr("src", s3Url);
            img.attr("style", "width: 100%;");
        }

        content = body.html(); // 수정된 body 내용으로 업데이트
      return content;
    }
}
