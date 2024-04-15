package com.aidharbor.Controller.US;

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
public class USContentController {

    private final ContactService contactService;
    private final CategoryService categoryService;

    // 문의 글 페이지
    @GetMapping(value = "/US/contact")
    public String UsContactView(Model model){

        List<ProductCategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("contactDTO",new ContactDTO());
        return "US/contact/contact";
    }

}