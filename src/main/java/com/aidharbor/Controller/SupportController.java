package com.aidharbor.Controller;

import com.aidharbor.DTO.CatalogDTO;
import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.PartnersDTO;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;
    private final CategoryService categoryService;

    // Support Video List View
    @GetMapping(value = "/support/videoList")
    public String videoList(Model model){
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<VideoBoardDTO> videoListDTO = supportService.videoList();

        model.addAttribute("categories",categories);
       model.addAttribute("videoList",videoListDTO);
        return "video/videoList";
    }


    // user Guide List view
    @GetMapping(value = "/support/userGuide")
    public String userGuideList(Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<UserGuideDTO> userGuideDTO = supportService.guideList();


        model.addAttribute("categories",categories);
        model.addAttribute("userGuideDTO",userGuideDTO);
        return "userGuide/userGuide";
    }

    // Catalog List view
    @GetMapping(value = "/support/catalog")
    public String catalogList(Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();
        List<CatalogDTO> catalogDTOList = supportService.catalogList();


        model.addAttribute("categories",categories);
        model.addAttribute("catalogDTO",catalogDTOList);
        return "catalog/catalog";
    }


    // 비디오 추가 페이지
    @GetMapping("/admin/videoAdd")
    public String videoAddView(Model model,VideoBoardDTO videoBoardDTO){
        List<ProductCategoryDto> categories = categoryService.findAll();

        model.addAttribute("categories",categories);
        model.addAttribute("videoBoardDTO",videoBoardDTO);
        return "video/videoForm";
    }

    // 비디오 수정 페이지
    @GetMapping("/admin/video/{videoId}")
    public String videoUpdateView(@PathVariable Long videoId, Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();

        VideoBoardDTO videoBoard = supportService.findByVideoId(videoId);

        model.addAttribute("categories",categories);
        model.addAttribute("videoBoardDTO",videoBoard);
        return "video/videoForm";
    }

    // 비디오 추가
    @PostMapping("/admin/videoAdd")
    public String videoAdd(@Valid VideoBoardDTO videoBoardDTO, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            return "video/videoForm";
        }
        try {
            supportService.videoAdd(videoBoardDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "비디오 등 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 비디오 수정 (어드민)
    @PostMapping(value = "/admin/video/{videoId}")
    public String videoUpdate(@PathVariable String videoId, VideoBoardDTO videoBoardDTO, Model model) {
        try {
            supportService.videoUpdate(videoBoardDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "비디오 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 비디오 삭제 (어드민)
    @PostMapping(value = "/admin/video/delete/{videoId}")
    public String videoDelete(@PathVariable String videoId, VideoBoardDTO videoBoardDTO, Model model) {
        try {
            supportService.videoDelete(videoBoardDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "비디오 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }
}
