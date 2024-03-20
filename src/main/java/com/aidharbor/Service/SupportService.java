package com.aidharbor.Service;

import com.aidharbor.DTO.VideoBoardDTO;
import com.aidharbor.Entity.VideoBoard;
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

    @Transactional(readOnly = true)
    public List<VideoBoardDTO> videoList() {
        List<VideoBoard> videoBoard = videoBoardRepository.findAll();
        List<VideoBoardDTO> videoBoardDTO = new ArrayList<>();

        for (VideoBoard board : videoBoard) {
            String videoUrl = board.getVideoUrl();
            int startIndex = videoUrl.indexOf("v=") + 2;
            String videoId = videoUrl.substring(startIndex);
            String imageUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

            VideoBoardDTO dto = new VideoBoardDTO(board.getId(), board.getTitle(), imageUrl,board.getProductCategory());
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
}
