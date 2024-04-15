package com.aidharbor.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainBannerDTO {
    private Long id;
    private String mainBannerImg;
    private String title;
    private String UsTitle;
    private String subtitle;
    private String UsSubtitle;
    private String bannerURL;
}
