package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryCreateRequest;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;

    // 전체 카테고리
    @GetMapping("/category/{categoryId}")
    @ResponseBody
    public ResponseEntity getParentCategoriesByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(categoryService.getChildCategories(categoryId));
    }

    // 카테고리 연결된 상품들
    @GetMapping("/category/product/{categoryId}")
    @ResponseBody
    public ResponseEntity getProductByCategoryId(@PathVariable int categoryId) {
        return ResponseEntity.ok(categoryService.getProductCategoryList(categoryId));
    }

}
