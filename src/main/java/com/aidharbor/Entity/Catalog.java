package com.aidharbor.Entity;


import com.aidharbor.DTO.CatalogDTO;
import com.aidharbor.DTO.UserGuideDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id")
    private int id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String UsTitle;

    @Column(length = 500)
    private String catalogURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory productCategory;


    public void updateFile(CatalogDTO catalogDTO, String storedFileName) {
        this.title = catalogDTO.getTitle();
        this.productCategory = catalogDTO.getProductCategory();
        this.catalogURL = storedFileName;
    }

    public void updateCatalog(CatalogDTO catalogDTO) {
        this.title = catalogDTO.getTitle();
        this.productCategory = catalogDTO.getProductCategory();
        this.catalogURL = catalogDTO.getCatalogURL();
    }

}
