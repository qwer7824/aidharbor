package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.EventDTO;
import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.DTO.Product.ProductResponseDTO;
import com.aidharbor.Service.AboutService;
import com.aidharbor.Service.BannerService;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CategoryService categoryService;
    private final BannerService bannerService;
    private final AboutService aboutService;
    private final ProductService productService;

    @GetMapping("/")
    public String Page(Model model) {

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<MainBannerDTO> mainBannerDTOs = bannerService.findByBannerList();
        List<ProductDTO> productAllList = productService.productAllList();
        List<EventDTO> Top3ListDTO = aboutService.Top3List();

        model.addAttribute("categories", categories);
        model.addAttribute("Top3List", Top3ListDTO);
        model.addAttribute("mainBannerDTO", mainBannerDTOs);
        model.addAttribute("productList", productAllList);

        return "home";
    }
}
