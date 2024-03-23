package com.aidharbor.Entity;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VideoBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory productCategory;


    public void updateVideo(VideoBoardDTO videoBoardDTO) {
        this.productCategory = videoBoardDTO.getProductCategory();
        this.title = videoBoardDTO.getTitle();
        this.videoUrl = videoBoardDTO.getVideoUrl();
    }
}
