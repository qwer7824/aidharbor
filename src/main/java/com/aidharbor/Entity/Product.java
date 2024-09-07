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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory productCategory;

    private String title;

    private String subTitle;

    private String explanation;

    private String explanation2;

    private String explanation3;

    private String UsTitle;

    private String UsSubTitle;

    private String UsExplanation;

    private String UsExplanation2;

    private String UsExplanation3;

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
        this.explanation = productDTO.getExplanation();
        this.explanation2 = productDTO.getExplanation2();
        this.explanation3 = productDTO.getExplanation3();
        this.UsExplanation = productDTO.getUsExplanation();
        this.UsExplanation2 = productDTO.getUsExplanation2();
        this.UsExplanation3 = productDTO.getUsExplanation3();
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
        this.explanation = productDTO.getExplanation();
        this.explanation2 = productDTO.getExplanation2();
        this.explanation3 = productDTO.getExplanation3();
        this.UsExplanation = productDTO.getUsExplanation();
        this.UsExplanation2 = productDTO.getUsExplanation2();
        this.UsExplanation3 = productDTO.getUsExplanation3();
        this.content = productDTO.getContent();
    }
}
