package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.PartnersDTO;
import com.aidharbor.Service.AboutService;
import com.aidharbor.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AboutController {

    private final CategoryService categoryService;
    private final AboutService aboutService;

    @GetMapping(value = "/about")
    public String aboutPage(Model model) {
        List<ProductCategoryDto> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        return "about/about";
    }

    @GetMapping(value = "/about/partners")
    public String partnersPage(Model model) {
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<PartnersDTO> partnersDTOList = aboutService.partnerView();

        model.addAttribute("partnerList", partnersDTOList);
        model.addAttribute("categories", categories);
        return "about/partners";
    }

    // 파트너 로고 생성 페이지
    @GetMapping(value = "/admin/partners")
    public String partnersAdminPage(PartnersDTO partnersDTO, Model model) {

        model.addAttribute("partnersDTO", partnersDTO);
        return "admin/partnersForm";
    }

    // 파트너 로고 생성
    @PostMapping(value = "/admin/partners/new")
    public String partnersAdd(@RequestPart(name = "Img") MultipartFile partnerImg, Model model) throws Exception {
        try {
            aboutService.partnerAdd(partnerImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "로고 등록 중 에러가 발생하였습니다.");
            return "admin/partnersForm";
        }
        return "redirect:/admin";
    }

    // 파트너 로고 수정 페이지
    @GetMapping(value = "/admin/partners/{partnersId}")
    public String partnersUpdateAdminPage(@PathVariable Long partnersId, Model model) {

        PartnersDTO partners = aboutService.findByPartnersId(partnersId);

        model.addAttribute("partnersDTO", partners);
        return "admin/partnersForm";
    }

    // 파트너 로고 수정 (어드민)
    @PostMapping(value = "/admin/partners/{partnersId}")
    public String partnersUpdate(@PathVariable String partnersId, PartnersDTO partnersDTO, @RequestParam(name = "Img") MultipartFile Img, Model model) {
        try {
            aboutService.partnerUpdate(partnersDTO, Img);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "로고 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 파트너 로고 삭제
    @PostMapping(value = "/admin/partners/delete/{partnersId}")
    public String partnersDelete(PartnersDTO partnersDTO, Model model) {
        try {
            aboutService.partnersDelete(partnersDTO.getId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "로고 삭제 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

}
