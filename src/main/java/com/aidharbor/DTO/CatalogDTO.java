package com.aidharbor.DTO;

import com.aidharbor.Entity.ProductCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogDTO {

    private Long id;

    @NotEmpty
    private String title;

    private String catalogURL;

    @NotNull(message = "ProductCategory Check")
    private ProductCategory productCategory;
}
