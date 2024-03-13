package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryCreateRequest;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    // 전체 카테고리
    @GetMapping("/category/{categoryId}")
    public ResponseEntity getParentCategoriesByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(categoryService.getChildCategories(categoryId));
    }

    // 카테고리 연결된 상품들
    @GetMapping("/category/product/{categoryId}")
    public ResponseEntity getProductByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(categoryService.getProductCategoryList(categoryId));
    }


}
