package com.aidharbor.DTO;

import com.aidharbor.Entity.ProductCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGuideDTO {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String UsTitle;

    private String guideURL;

    @NotNull(message = "ProductCategory Check")
    private ProductCategory productCategory;
}
