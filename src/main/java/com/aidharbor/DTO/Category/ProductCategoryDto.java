package com.aidharbor.DTO.Category;

import com.aidharbor.Entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class ProductCategoryDto {
    private int id;
    private String name;
    private List<ProductCategoryDto> children;


    public static List<ProductCategoryDto> toDtoList(List<ProductCategory> categories) {
        ProductCategoryHelper helper = ProductCategoryHelper.newInstance(
                categories,
                c -> new ProductCategoryDto(c.getId(), c.getName(), new ArrayList<>()),
                ProductCategory::getParent,
                ProductCategory::getId,
                ProductCategoryDto::getChildren);
        return helper.convert();
    }
    public ProductCategoryDto() {
    }
}
