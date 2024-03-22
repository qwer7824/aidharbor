package com.aidharbor.Service;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.DTO.UserGuideDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import com.aidharbor.Entity.UserGuide;
import com.aidharbor.Entity.VideoBoard;
import com.aidharbor.Repository.UserGuideRepository;
import com.aidharbor.Repository.VideoBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SupportService {
    private final VideoBoardRepository videoBoardRepository;
    private final UserGuideRepository userGuideRepository;
    private final ModelMapper modelMapper;


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

    public void videoAdd(VideoBoardDTO videoBoardDTO) {

       VideoBoard videoBoard = VideoBoard.builder()
                        .title(videoBoardDTO.getTitle())
                        .videoUrl(videoBoardDTO.getVideoUrl())
                        .productCategory(videoBoardDTO.getProductCategory())
                        .build();

        videoBoardRepository.save(videoBoard);

    }

    public List<UserGuideDTO> guideList() {
        List<UserGuide> userGuideList = userGuideRepository.findAll();

        List<UserGuideDTO> userGuideDTOList = userGuideList.stream()
                .map(userGuide -> modelMapper.map(userGuide, UserGuideDTO.class))
                .collect(Collectors.toList());

        return userGuideDTOList;
    }
}