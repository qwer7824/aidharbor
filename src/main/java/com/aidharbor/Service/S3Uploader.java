package com.aidharbor.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        String fileName = generateFileName(multipartFile.getOriginalFilename());  // 파일 이름을 생성
        String uploadImageUrl = putS3(uploadFile, dirName + "/" + fileName);

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    public String uploadFile(File file, String dirName) throws IOException {

        String fileName = dirName + "/" + generateFileName(file.getName());  // 파일 이름을 생성
        String uploadImageUrl = putS3(file, fileName);

        removeNewFile(file);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String generateFileName(String originalFileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");  // 년월일 형식으로 파일 이름 생성
        String currentDate = sdf.format(new Date());
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));  // 파일 확장자 추출
        String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf("."));  // 확장자 제외한 파일 이름 추출
        return currentDate + "_" + fileNameWithoutExtension + extension;
    }

}
