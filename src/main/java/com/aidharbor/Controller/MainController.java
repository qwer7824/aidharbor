package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.EventDTO;
import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.Service.AboutService;
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
    private final AboutService aboutService;

    @GetMapping("/")
    public String Page(Model model) {

        // TODO : 이벤트 탭 미구현 (백 , 프론트)
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<MainBannerDTO> mainBannerDTOs = bannerService.findByBannerList();
        List<EventDTO> Top3ListDTO = aboutService.Top3List();

        model.addAttribute("categories", categories);
        model.addAttribute("Top3List", Top3ListDTO);
        model.addAttribute("mainBannerDTO", mainBannerDTOs);
        return "home";
    }
}
