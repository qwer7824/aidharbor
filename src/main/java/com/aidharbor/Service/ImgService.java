package com.aidharbor.Service;

import com.aidharbor.DTO.Image.DisplayedImageDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
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

    public DisplayedImageDTO saveTemporaryImage(MultipartFile ImgFile) throws IOException {
        String oriImgName = ImgFile.getOriginalFilename();
        String imgUrl = "";

        if (StringUtils.isEmpty(oriImgName)) {
            throw new IllegalArgumentException();
        }

        imgUrl = s3Uploader.upload(ImgFile,"images");

        return DisplayedImageDTO.builder()
                .originalName(oriImgName)
                .savedPath(imgUrl)
                .build();
    }

    public void imgProductDelete(Product product) throws IOException {
        String titleUrl = imgSubString(product.getTitleImgUrl());
        String content = product.getContent();

        DeleteConvert(content);
        s3Uploader.deleteFile(titleUrl);
    }

    public String imgSubString(String url){
        String convertUrl = url.substring(url.lastIndexOf('/') + 1);
        return convertUrl;
    }

    public void DeleteConvert(String content) throws IOException {
        Document doc = Jsoup.parse(content);

        Element body = doc.body(); // body 요소 가져오기

        for (Element img : body.select("img")) {
            String src = img.attr("src"); // 현재 src 속성 값 가져오기
            String fileName = src.substring(src.lastIndexOf('/') + 1); // '/' 뒤의 문자열(파일 이름) 추출
            s3Uploader.deleteFile(fileName);
        }
    }


    public String convert(ProductDTO productDTO) throws IOException {
        String content = productDTO.getContent();
        Document doc = Jsoup.parse(content);

        Element body = doc.body(); // body 요소 가져오기

        for (Element img : body.select("img")) {
            img.attr("style", "width: 100%;");
        }

        content = body.html(); // 수정된 body 내용으로 업데이트
      return content;
    }


    public void imgCategoryDelete(ProductCategory productCategory) throws IOException {
        String titleUrl = imgSubString(productCategory.getCategoryImg());

        s3Uploader.deleteFile(titleUrl);
    }
}
