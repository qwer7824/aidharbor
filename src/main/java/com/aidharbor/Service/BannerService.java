package com.aidharbor.Service;

import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import com.aidharbor.Entity.MainBanner;
import com.aidharbor.Entity.VideoBoard;
import com.aidharbor.Repository.MainBannerRepository;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final ModelMapper modelMapper;
    private final MainBannerRepository mainBannerRepository;

    public List<MainBannerDTO> findByBanner() {
        List<MainBanner> mainBanners = mainBannerRepository.findAll();
        return mainBanners.stream()
                .map(mainBanner -> modelMapper.map(mainBanner, MainBannerDTO.class))
                .collect(Collectors.toList());
    }
}
