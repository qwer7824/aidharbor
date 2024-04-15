package com.aidharbor.Entity;


import com.aidharbor.DTO.UserGuideDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userGuide_id")
    private int id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String UsTitle;

    @Column(length = 500)
    private String guideURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory productCategory;

    public void updateFile(UserGuideDTO userGuideDTO, String storedFileName) {
        this.title = userGuideDTO.getTitle();
        this.productCategory = userGuideDTO.getProductCategory();
        this.guideURL = storedFileName;
    }

    public void updateUserGuide(UserGuideDTO userGuideDTO) {
        this.title = userGuideDTO.getTitle();
        this.productCategory = userGuideDTO.getProductCategory();
        this.guideURL = userGuideDTO.getGuideURL();
    }
}
