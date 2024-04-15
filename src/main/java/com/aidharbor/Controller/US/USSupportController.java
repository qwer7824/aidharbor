package com.aidharbor.Controller.US;

import com.aidharbor.DTO.CatalogDTO;
import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.UserGuideDTO;
import com.aidharbor.DTO.Video.VideoBoardDTO;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.SupportService;
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

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class USSupportController {

    private final SupportService supportService;
    private final CategoryService categoryService;

    // Support Video List View
    @GetMapping(value = "/US/support/videoList")
    public String videoList(Model model){
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<VideoBoardDTO> videoListDTO = supportService.videoList();

        model.addAttribute("categories",categories);
       model.addAttribute("videoList",videoListDTO);
        return "US/video/videoList";
    }


    // user Guide List view
    @GetMapping(value = "/US/support/userGuide")
    public String UsUserGuideList(Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<UserGuideDTO> userGuideDTO = supportService.guideList();


        model.addAttribute("categories",categories);
        model.addAttribute("userGuideDTO",userGuideDTO);
        return "US/userGuide/userGuide";
    }

    // Catalog List view
    @GetMapping(value = "/US/support/catalog")
    public String UsCatalogList(Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<CatalogDTO> catalogDTOList = supportService.catalogList();


        model.addAttribute("categories",categories);
        model.addAttribute("catalogDTO",catalogDTOList);
        return "US/catalog/catalog";
    }

}
