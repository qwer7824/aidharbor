package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Repository.ProductRepository;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    // 상품 리스트
    @GetMapping(value = "/product/{categoryId}")
    public String productListView(@PathVariable int categoryId, Model model) {
        List<ProductDTO> product = productService.productList(categoryId);
        List<ProductCategoryDto> categories = categoryService.findAll();

        if (categoryId == 0) {
            model.addAttribute("All", true);
        }

        model.addAttribute("categoryId",categoryId);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "product/productList";
    }

    // 상품 상세
    @GetMapping(value = "/product/detail/{productId}")
    public String productDetailView(@PathVariable long productId, Model model) {
        try {
            ProductDTO product = productService.getProductDetail(productId);
            List<ProductCategoryDto> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "No product");
        }
        return "product/productDetail";
    }

    // 상품 수정 페이지 (어드민)
    @GetMapping(value = "/admin/product/{productId}")
    public String ProductDetail(@PathVariable(name = "productId") Long productId, Model model) {
        try {
            ProductDTO productDTO = productService.getProductDetail(productId);
            List<ProductCategoryDto> categories = categoryService.findAll();
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
        }
        return "product/productForm";
    }

    // 상품 수정 (어드민)
    @PostMapping(value = "/admin/product/{productId}")
    public String productUpdate(@Valid ProductDTO productDTO,BindingResult bindingResult, @RequestParam(name = "titleImg") MultipartFile titleImg, Model model) {

        if (bindingResult.hasErrors()) {
            return "product/productForm";
        }

        try {
            productService.productUpdate(productDTO,titleImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 상품 삭제 (어드민)
    @PostMapping(value = "/admin/product/delete/{prodcutId}")
    public String productDelete(ProductDTO productDTO,Model model){
        try{
            productService.productDelete(productDTO.getId());
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 삭제 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 상품 등록 페이지 (어드민)
    @GetMapping(value = "/admin/product/new")
    public String productForm(ProductDTO productDTO, Model model) {

        List<ProductCategoryDto> categories = categoryService.findAll();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categories);
        return "product/productForm";
    }

    // 상품 등록 (어드민)
    @PostMapping(value = "/admin/product/new")
    public String productNew(@Valid ProductDTO productDTO,BindingResult bindingResult, @RequestParam(name = "titleImg") MultipartFile titleImg, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "product/productForm";
        }
        try{
            productService.productSave(productDTO,titleImg);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "product/productForm";
        }
        return "redirect:/admin";
    }

}
