package com.aidharbor.Controller;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.Contact.ContactDTO;
import com.aidharbor.Service.CategoryService;
import com.aidharbor.Service.ContactService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentController {

    private final ContactService contactService;
    private final CategoryService categoryService;

    // 문의 글 페이지
    @GetMapping(value = "/contact")
    public String contactView(Model model){

        // TODO : 미구현 (프론트 동의)
        List<ProductCategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("contactDTO",new ContactDTO());
        return "contact";
    }

    // 문의 넣기
    @PostMapping(value = "/contactAdd")
    public String contactAdd(@Valid ContactDTO contactDTO,BindingResult bindingResult, Model model) throws MethodArgumentNotValidException, MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return "contact";
        }
        try{
            contactService.contactAdd(contactDTO);
            model.addAttribute("errorMessage", "Sent successfully");
        }catch (Exception e){
            model.addAttribute("errorMessage", "An error occurred");
        }
        return "contact";
    }

}