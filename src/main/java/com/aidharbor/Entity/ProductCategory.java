package com.aidharbor.Entity;

import com.aidharbor.DTO.Category.ProductCategoryCreateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Setter
@Builder
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory parent;

    private String categorySubTitle;

    private String categoryUsSubTitle;

    private String categoryImg;

    private String categoryMainImg;

    private String categoryImgTitle;

    private String categoryMiddleTitle;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductCategory> children = new ArrayList<>();

    public ProductCategory(String name, ProductCategory parent,String categoryImg,String categorySubTitle,String categoryUsSubTitle,String categoryImgTitle,String categoryMiddleTitle,String categoryMainImg) {
        this.name = name;
        this.parent = parent;
        this.categoryUsSubTitle = categoryUsSubTitle;
        this.categoryImg = categoryImg;
        this.categorySubTitle = categorySubTitle;
        this.categoryImgTitle = categoryImgTitle;
        this.categoryMainImg = categoryMainImg;
        this.categoryMiddleTitle= categoryMiddleTitle;
    }

    public void CategoryUpdate(ProductCategoryCreateRequest req) {
        this.name = req.getName();
        this.categoryUsSubTitle = req.getCategoryUsSubTitle();
        this.categorySubTitle = req.getCategorySubTitle();
        this.categoryImgTitle = req.getCategoryImgTitle();
        this.categoryMiddleTitle= req.getCategoryMiddleTitle();
    }

    public void updateImgUrl(ProductCategoryCreateRequest req, String storedFileName) {
        this.name = req.getName();
        this.categoryImg = storedFileName;
        this.categoryUsSubTitle = req.getCategoryUsSubTitle();
        this.categorySubTitle = req.getCategorySubTitle();
        this.categoryImgTitle = req.getCategoryImgTitle();
        this.categoryMiddleTitle= req.getCategoryMiddleTitle();
    }
    public void updateImgUrl2(ProductCategoryCreateRequest req, String storedFileName2) {
        this.name = req.getName();
        this.categoryMainImg = storedFileName2;
        this.categorySubTitle = req.getCategorySubTitle();
        this.categoryImgTitle = req.getCategoryImgTitle();
        this.categoryMiddleTitle= req.getCategoryMiddleTitle();
    }
}
