package com.aidharbor.Controller.US;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class USProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    // 상품 리스트
    @GetMapping(value = "/US/product/{categoryId}")
    public String UsProductListView(@PathVariable int categoryId, Model model) {
        List<ProductDTO> product = productService.productList(categoryId);
        List<ProductCategoryDto> categories = categoryService.findAll();

        if (categoryId == 0) {
            model.addAttribute("All", true);
        }

        model.addAttribute("categoryId",categoryId);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "US/product/productList";
    }

    // 상품 상세
    @GetMapping(value = "/US/product/detail/{productId}")
    public String UsProductDetailView(@PathVariable long productId, Model model) {
        try {
            ProductDTO product = productService.getProductDetail(productId);
            List<ProductCategoryDto> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "No product");
        }
        return "US/product/productDetail";
    }
}
