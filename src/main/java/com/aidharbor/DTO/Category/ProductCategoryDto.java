package com.aidharbor.DTO.Category;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class ProductCategoryDto {
    private int id;
    private String name;
    private String categoryImg;
    private String categorySubTitle;
    private String categoryUsSubTitle;
    private List<ProductCategoryDto> children;


    public static List<ProductCategoryDto> toDtoList(List<ProductCategory> categories) {
        ProductCategoryHelper helper = ProductCategoryHelper.newInstance(
                categories,
                c -> new ProductCategoryDto(c.getId(), c.getName(),c.getCategoryImg(),c.getCategorySubTitle(),c.getCategoryUsSubTitle(), new ArrayList<>()),
                ProductCategory::getParent,
                ProductCategory::getId,
                ProductCategoryDto::getChildren);
        return helper.convert();
    }
    public ProductCategoryDto() {
    }
}
