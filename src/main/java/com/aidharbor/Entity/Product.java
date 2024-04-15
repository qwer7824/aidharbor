package com.aidharbor.Entity;

import com.aidharbor.DTO.Product.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory productCategory;

    private String title;

    private String subTitle;

    private String UsTitle;

    private String UsSubTitle;

    private String titleImgUrl;

    @Lob
    @Column(columnDefinition="MEDIUMTEXT")
    private String content;

    public void updateProduct(ProductDTO productDTO) {
        this.productCategory = productDTO.getProductCategory();
        this.title = productDTO.getTitle();
        this.UsTitle = productDTO.getUsTitle();
        this.subTitle = productDTO.getSubTitle();
        this.UsSubTitle = productDTO.getUsSubTitle();
        this.titleImgUrl = productDTO.getTitleImgUrl();
        this.content = productDTO.getContent();
    }
    public void updateTitleImgUrl(ProductDTO productDTO,String titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
        this.productCategory = productDTO.getProductCategory();
        this.title = productDTO.getTitle();
        this.UsTitle = productDTO.getUsTitle();
        this.subTitle = productDTO.getSubTitle();
        this.UsSubTitle = productDTO.getUsSubTitle();
        this.content = productDTO.getContent();
    }
}
