package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryCreateRequest;
import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.Contact.ContactDTO;
import com.aidharbor.DTO.MainBannerDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Enum.ContactState;
import com.aidharbor.Service.BannerService;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ContactService;
import com.aidharbor.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final BannerService bannerService;

    private final ContactService contactService;

    @GetMapping(value = "/admin")
    public String adminDashBoard(Model model){

        List<ProductCategoryDto> categoryDto = categoryService.findAll();
        List<ProductDTO> productDTO = productService.productList(0);
        Map<ContactState, Integer> contactCounts = contactService.getContactCounts();
        List<ContactDTO> contactList = contactService.findAll();

        model.addAttribute("product",productDTO);
        model.addAttribute("category",categoryDto);
        model.addAttribute("contactCounts",contactCounts);
        model.addAttribute("contactList",contactList);
        return "admin/dashboard";
    }

    // 어드민 - 문의 내역 리스트
    @GetMapping(value = "/admin/contactList")
    public String adminContactListView(Model model){
        List<ContactDTO> ContactDTO = contactService.findAll();

        model.addAttribute("contact",ContactDTO);
        return "contact/contactList";
    }

    // 문의 상세
    @GetMapping(value = "/admin/contact/{contactId}")
    public String adminContactDetailView(Model model, @PathVariable Long contactId){
        ContactDTO ContactDTO = contactService.findById(contactId);

        model.addAttribute("contact",ContactDTO);
        return "contact/contactDetail";
    }
    // 문의 체크
    @PostMapping(value = "/admin/contact/{contactId}")
    public String adminContactCheck(@PathVariable Long contactId){
        contactService.contactCheck(contactId);
        return "redirect:/admin";
    }

    // 메인 베너 추가 페이지
    @GetMapping(value = "/admin/banner/bannerAdd")
    public String bannerAddView(MainBannerDTO mainBannerDTO, Model model){
        model.addAttribute("mainBannerDTO", mainBannerDTO);
        return "admin/mainBannerForm";
    }

    // 메인 배너 리스트
    @GetMapping(value = "/admin/banner/bannerList")
    public String bannerListView( Model model){
        List<MainBannerDTO> mainBannerDTO = bannerService.findByBannerList();
        model.addAttribute("mainBanner", mainBannerDTO);
        return "admin/mainBannerList";
    }

    // 배너 추가
    @PostMapping(value = "/admin/banner/bannerAdd")
    public String bannerAdd(@Valid MainBannerDTO mainBannerDTO, BindingResult bindingResult, @RequestPart(name = "Img") MultipartFile bannerImg, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/mainBannerForm";
        }
        try {
            bannerService.bannerCreate(mainBannerDTO,bannerImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "배너 추가 중 에러가 발생하였습니다.");
        }
        model.addAttribute("mainBannerDTO",mainBannerDTO);
        return "redirect:/admin";
    }

    // 배너 수정 페이지
    @GetMapping(value = "/admin/banner/{mainBannerId}")
    public String bannerUpdateView(@PathVariable Long mainBannerId, Model model){

        try {
            MainBannerDTO mainBannerDTO = bannerService.findByBanner(mainBannerId);
            model.addAttribute("mainBannerDTO",mainBannerDTO);
        }catch (Exception e){
            model.addAttribute("errorMessage", "배너가 없습니다.");
        }
        return "admin/mainBannerForm";
    }

    // 배너 수정
    @PostMapping(value = "/admin/banner/{categoryId}")
    public String bannerUpdate(@Valid MainBannerDTO mainBannerDTO,BindingResult bindingResult,@RequestPart(name = "Img") MultipartFile bannerImg, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/mainBannerForm";
        }
        try {
            bannerService.bannerUpdate(mainBannerDTO,bannerImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "배너 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 배너 삭제
    @PostMapping("/admin/banner/delete/{bannerId}")
    public String bannerDelete(@PathVariable Long bannerId) throws IOException {
        bannerService.delete(bannerId);
        return "redirect:/admin";
    }

    // 카테고리 추가 페이지
    @GetMapping(value = "/admin/category/categoryAdd")
    public String categoryAddView(ProductCategoryCreateRequest categoryDTO, Model model){
        List<ProductCategoryDto> categoryList = categoryService.findAll();

        model.addAttribute("categories",categoryList);
        model.addAttribute("categoryDTO", categoryDTO);
        return "admin/categoryForm";
    }
    // 카테고리 리스트 페이지
    @GetMapping(value = "/admin/categoryList")
    public String categoryListView(Model model){
        List<ProductCategoryDto> categoryList = categoryService.findAll();

        model.addAttribute("categories",categoryList);
        return "admin/categoryList";
    }

    // 카테고리 추가
    @PostMapping(value = "/admin/category/categoryAdd")
    public String categoryAdd(@Valid ProductCategoryCreateRequest categoryDTO, BindingResult bindingResult, @RequestPart(name = "Img") MultipartFile categoryImg, @RequestPart(name = "Img2") MultipartFile categoryMainImg, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/categoryForm";
        }
        try {
            categoryService.create(categoryDTO,categoryImg,categoryMainImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "카테고리 추가 중 에러가 발생하였습니다.");
        }
        model.addAttribute("categoryDTO",categoryDTO);
        return "redirect:/admin";
    }

    // 카테고리 삭제
    @PostMapping("/admin/category/delete/{id}")
    public String categoryDelete(@PathVariable int id) throws IOException {
        categoryService.delete(id);
        return "redirect:/admin";
    }

    // 카테고리 수정 페이지
    @GetMapping(value = "/admin/category/{categoryId}")
    public String categoryUpdateView(@PathVariable int categoryId, Model model){

        try {
            ProductCategoryCreateRequest categoryDTO =categoryService.categoryDetail(categoryId);
            model.addAttribute("categoryDTO",categoryDTO);
        }catch (Exception e){
            model.addAttribute("errorMessage", "카테고리가 없습니다.");
        }
        return "admin/categoryForm";
    }

    // 카테고리 업데이트
    @PostMapping(value = "/admin/category/{categoryId}")
    public String categoryUpdate(@Valid ProductCategoryCreateRequest req,BindingResult bindingResult,@RequestPart(name = "Img") MultipartFile categoryImg,@RequestPart(name = "Img2") MultipartFile categoryImg2, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/categoryForm";
        }
        try {
            categoryService.categoryUpdate(req,categoryImg,categoryImg2);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "카테고리 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }
}
