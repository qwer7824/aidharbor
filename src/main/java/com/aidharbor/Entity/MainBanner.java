package com.aidharbor.Entity;

import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MainBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mainBanner_id")
    private Long id;

    @Column(length = 500)
    private String mainBannerImg;
    private String title;
    private String UsTitle;
    private String subtitle;
    private String UsSubtitle;
    private String bannerURL;

    public void updateMainBannerImg(MainBannerDTO mainBannerDTO,String ImgUrl) {
        this.mainBannerImg = ImgUrl;
        this.title = mainBannerDTO.getTitle();
        this.UsTitle = mainBannerDTO.getUsTitle();
        this.subtitle = mainBannerDTO.getSubtitle();
        this.UsSubtitle = mainBannerDTO.getUsSubtitle();
        this.bannerURL = mainBannerDTO.getBannerURL();
    }
    public void updateMainBanner(MainBannerDTO mainBannerDTO) {
        this.mainBannerImg = mainBannerDTO.getMainBannerImg();
        this.title = mainBannerDTO.getTitle();
        this.UsTitle = mainBannerDTO.getUsTitle();
        this.subtitle = mainBannerDTO.getSubtitle();
        this.UsSubtitle = mainBannerDTO.getUsSubtitle();
        this.bannerURL = mainBannerDTO.getBannerURL();
    }
}
