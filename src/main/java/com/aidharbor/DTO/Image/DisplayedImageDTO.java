package com.aidharbor.DTO.Image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisplayedImageDTO {
    private String originalName; // 원본 이름
    private String savedPath; // 저장된 파일 Path
}