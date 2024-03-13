package com.aidharbor.DTO.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;

    private String title;

    private String titleImgUrl;

}
