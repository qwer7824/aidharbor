package com.aidharbor.Controller.US;

import com.aidharbor.DTO.Category.ProductCategoryDto;
import com.aidharbor.DTO.EventDTO;
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
public class USAboutController {

    private final CategoryService categoryService;
    private final AboutService aboutService;

    @GetMapping(value = "/US/about")
    public String UsAboutPage(Model model) {
        List<ProductCategoryDto> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        return "US/about/about";
    }

    @GetMapping(value = "/US/about/partners")
    public String UsPartnersPage(Model model) {
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<PartnersDTO> partnersDTOList = aboutService.partnerView();

        model.addAttribute("partnerList", partnersDTOList);
        model.addAttribute("categories", categories);
        return "US/about/partners";
    }

    // CalendarOfEventsView
    @GetMapping(value = "/US/about/CalendarOfEvents")
    public String UsCalendarOfEventsView(Model model, @RequestParam(defaultValue = "0") int page){
        int size = 4; // 한 페이지에 표시할 아이템 수
        List<ProductCategoryDto> categories = categoryService.findAll();
        List<EventDTO> eventListDTO = aboutService.findEventsByPage(page, size);

        // 전체 이벤트 개수
        long totalEvents = aboutService.getTotalEventsCount();
        // 전체 페이지 수
        int totalPages = (int) Math.ceil((double) totalEvents / size);


        model.addAttribute("eventList", eventListDTO);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categories", categories);
        return "US/about/event";
    }

    // 캘리던 및 이벤트 디테일 뷰
    @GetMapping(value = "/US/about/CalendarOfEvents/{eventId}")
    public String UsCalendarOfEventsDetailView(@PathVariable Long eventId, Model model){
        List<ProductCategoryDto> categories = categoryService.findAll();
        EventDTO EventDTO = aboutService.CalenderOfEventDetail(eventId);

        model.addAttribute("event", EventDTO);
        model.addAttribute("categories", categories);
        return "US/about/eventDetail";
    }

}
