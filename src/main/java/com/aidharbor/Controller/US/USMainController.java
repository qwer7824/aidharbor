package com.aidharbor.Controller.US;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.EventDTO;
import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Service.AboutService;
import com.aidharbor.Service.BannerService;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class USMainController {

    private final CategoryService categoryService;
    private final BannerService bannerService;
    private final AboutService aboutService;
    private final ProductService productService;

    @GetMapping("/US")
    public String USPage(Model model) {

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<MainBannerDTO> mainBannerDTOs = bannerService.findByBannerList();
        List<EventDTO> Top3ListDTO = aboutService.Top3List();
        List<ProductDTO> productAllList = productService.productAllList();

        model.addAttribute("productList", productAllList);
        model.addAttribute("categories", categories);
        model.addAttribute("Top3List", Top3ListDTO);
        model.addAttribute("mainBannerDTO", mainBannerDTOs);
        return "US/home";
    }

    @GetMapping("/US/categories")
    @ResponseBody
    public List<List<ProductCategoryDto>> getCategories() {
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<List<ProductCategoryDto>> childrenCategories = new ArrayList<>();

        for (ProductCategoryDto category : categories) {
            childrenCategories.add(category.getChildren());
        }

        return childrenCategories;
    }

}
