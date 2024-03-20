package com.aidharbor.DTO;

import com.aidharbor.Entity.ProductCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoBoardDTO {

    private Long id;

    @NotEmpty
    private String title;

    @Pattern(regexp = "https://www.youtube.com/watch\\?v=.+",message = "양식에 맞지 않습니다.")
    private String videoUrl;

    @NotNull(message = "ProductCategory Check")
    private ProductCategory productCategory;


}
