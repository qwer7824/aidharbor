package com.aidharbor.Service;

import com.aidharbor.DTO.CatalogDTO;
import com.aidharbor.DTO.UserGuideDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import com.aidharbor.Entity.*;
import com.aidharbor.Repository.CatalogRepository;
import com.aidharbor.Repository.UserGuideRepository;
import com.aidharbor.Repository.VideoBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SupportService {
    private final VideoBoardRepository videoBoardRepository;
    private final UserGuideRepository userGuideRepository;
    private final CatalogRepository catalogRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;
    private final ImgService imgService;


    @Transactional(readOnly = true)
    public List<VideoBoardDTO> videoList() {
        List<VideoBoard> videoBoard = videoBoardRepository.findAll();
        List<VideoBoardDTO> videoBoardDTO = new ArrayList<>();

        for (VideoBoard board : videoBoard) {
            String videoUrl = board.getVideoUrl();
            int startIndex = videoUrl.indexOf("v=") + 2;
            String videoId = videoUrl.substring(startIndex);
            String imageUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
            String video = "https://youtube.com/embed/" + videoId;

            VideoBoardDTO dto = new VideoBoardDTO(board.getId(), board.getTitle(), imageUrl,video, board.getProductCategory());
            videoBoardDTO.add(dto);
        }

        return videoBoardDTO;
    }

    @Transactional
    public void videoAdd(VideoBoardDTO videoBoardDTO) {

       VideoBoard videoBoard = VideoBoard.builder()
                        .title(videoBoardDTO.getTitle())
                        .videoUrl(videoBoardDTO.getVideoUrl())
                        .productCategory(videoBoardDTO.getProductCategory())
                        .build();

        videoBoardRepository.save(videoBoard);

    }
    @Transactional(readOnly = true)
    public List<UserGuideDTO> guideList() {
        List<UserGuide> userGuideList = userGuideRepository.findAll();

        List<UserGuideDTO> userGuideDTOList = userGuideList.stream()
                .map(userGuide -> modelMapper.map(userGuide, UserGuideDTO.class))
                .collect(Collectors.toList());

        return userGuideDTOList;
    }

    @Transactional(readOnly = true)
    public List<CatalogDTO> catalogList() {
        List<Catalog> catalogList = catalogRepository.findAll();

        List<CatalogDTO> catalogDTOList = catalogList.stream()
                .map(catalog -> modelMapper.map(catalog, CatalogDTO.class))
                .collect(Collectors.toList());

        return catalogDTOList;
    }

    @Transactional(readOnly = true)
    public VideoBoardDTO findByVideoId(Long videoId) {
        VideoBoard videoBoard = videoBoardRepository.findById(videoId).orElse(null);
        VideoBoardDTO dto = modelMapper.map(videoBoard, VideoBoardDTO.class);
        return dto;
    }

    @Transactional
    public void videoUpdate(VideoBoardDTO videoBoardDTO) {
        VideoBoard videoBoard = videoBoardRepository.findById(videoBoardDTO.getId()).orElse(null);

        videoBoard.updateVideo(videoBoardDTO);

        videoBoardRepository.save(videoBoard);
        }

        @Transactional
    public void videoDelete(VideoBoardDTO videoBoardDTO) {

        VideoBoard videoBoard = videoBoardRepository.findById(videoBoardDTO.getId()).orElse(null);

        videoBoardRepository.delete(videoBoard);
    }

    @Transactional
    public void userGuideAdd(UserGuideDTO userGuideDTO, MultipartFile guideFile) throws IOException {
        String storedFileName = s3Uploader.upload(guideFile, "pdf");

        UserGuide userGuide = UserGuide.builder()
                .productCategory(userGuideDTO.getProductCategory())
                .title(userGuideDTO.getTitle())
                .guideURL(storedFileName)
                .build();

        userGuideRepository.save(userGuide);
    }

    @Transactional
    public void userGuideUpdate(UserGuideDTO userGuideDTO, MultipartFile guideFile) throws IOException {
        UserGuide userGuide = userGuideRepository.findById(userGuideDTO.getId()).orElseThrow(null);

        if (!guideFile.isEmpty()) {
            String url = imgService.imgSubString(userGuideDTO.getGuideURL());
            s3Uploader.deleteFile(url);
            String storedFileName = s3Uploader.upload(guideFile, "pdf");
            userGuide.updateFile(userGuideDTO,storedFileName);
        } else {
            userGuide.updateUserGuide(userGuideDTO);
        }
        userGuideRepository.save(userGuide);

    }

    @Transactional
    public UserGuideDTO findByUserGuideId(Long userGuideId) {
        UserGuide userGuide = userGuideRepository.findById(userGuideId).orElseThrow(null);
        UserGuideDTO dto = modelMapper.map(userGuide, UserGuideDTO.class);
        return dto;
    }

    @Transactional
    public void userGuideDelete(UserGuideDTO userGuideDTO) throws IOException {
        UserGuide userGuide = userGuideRepository.findById(userGuideDTO.getId()).orElse(null);
        imgService.pdfDelete(userGuide.getGuideURL());

        userGuideRepository.delete(userGuide);
    }
}
