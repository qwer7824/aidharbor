package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.Service.BannerService;
import com.aidharbor.Service.CategoryService;
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

    @GetMapping("/")
    public String Page(Model model) {

        // TODO : 이벤트 탭 미구현 (백 , 프론트)
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<MainBannerDTO> mainBannerDTOs = bannerService.findByBanner();

        model.addAttribute("categories", categories);
        model.addAttribute("mainBannerDTO", mainBannerDTOs);
        return "home";
    }
}
