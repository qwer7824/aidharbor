package com.aidharbor.DTO.Product;

import com.aidharbor.Entity.Product;
import com.aidharbor.Entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotNull(message = "ProductCategory Check")
    private ProductCategory productCategory;

    @NotEmpty(message = "Title Check")
    private String title;

    @NotEmpty(message = "subTitle Check")
    private String subTitle;

    private String titleImgUrl;

    private String content;

    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static ProductDTO of(Product product) {
        return modelMapper.map(product,ProductDTO.class);
    }
}
