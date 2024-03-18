package com.aidharbor.DTO.Category;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryCreateRequest {

    private Integer id;

    @NotBlank(message = "카테고리 명을 입력하세요.")
    @Size(min = 2, max = 15, message = "길이 제한은 2~15자 이내입니다.")
    private String name;

    private String categoryImg;

    private Integer parentId;

    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static ProductCategoryCreateRequest of(ProductCategory productCategory) {
        return modelMapper.map(productCategory,ProductCategoryCreateRequest.class);
    }

}