package com.aidharbor.Service;

import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import com.aidharbor.Entity.MainBanner;
import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
import com.aidharbor.Entity.VideoBoard;
import com.aidharbor.Excption.CategoryNotFoundException;
import com.aidharbor.Repository.MainBannerRepository;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final ModelMapper modelMapper;
    private final MainBannerRepository mainBannerRepository;
    private final S3Uploader s3Uploader;
    private final ImgService imgService;

    public List<MainBannerDTO> findByBannerList() {
        List<MainBanner> mainBanners = mainBannerRepository.findAll();
        return mainBanners.stream()
                .map(mainBanner -> modelMapper.map(mainBanner, MainBannerDTO.class))
                .collect(Collectors.toList());
    }

    public MainBannerDTO findByBanner(Long mainBannerId) {
        MainBanner mainBanner = mainBannerRepository.findById(mainBannerId).orElseThrow(null);
        MainBannerDTO dto = modelMapper.map(mainBanner,MainBannerDTO.class);
        return dto;
    }

    @Transactional
    public void bannerCreate(MainBannerDTO mainBannerDTO, MultipartFile bannerImg) throws IOException {
        String storedFileName = s3Uploader.upload(bannerImg, "images");

        MainBanner mainBanner = MainBanner.builder()
                .title(mainBannerDTO.getTitle())
                .subtitle(mainBannerDTO.getSubtitle())
                .mainBannerImg(storedFileName)
                .build();

        mainBannerRepository.save(mainBanner);
    }

@Transactional
    public void bannerUpdate(MainBannerDTO mainBannerDTO, MultipartFile bannerImg) throws IOException {
        MainBanner banner = mainBannerRepository.findById(mainBannerDTO.getId()).orElseThrow(null);

        if (!bannerImg.isEmpty()) {
            String url = imgService.imgSubString(mainBannerDTO.getMainBannerImg());
            s3Uploader.deleteFile(url);
            String storedFileName = s3Uploader.upload(bannerImg, "images");
            banner.updateMainBannerImg(mainBannerDTO,storedFileName);
        } else {
            banner.updateMainBanner(mainBannerDTO);
        }
        mainBannerRepository.save(banner);
    }

    @Transactional
    public void delete(Long bannerId) throws IOException {
        MainBanner mainBanner = mainBannerRepository.findById(bannerId).orElseThrow(CategoryNotFoundException::new);
        imgService.imgBannerDelete(mainBanner);
        mainBannerRepository.delete(mainBanner);
    }
}
