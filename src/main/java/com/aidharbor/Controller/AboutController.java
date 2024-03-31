package com.aidharbor.Controller;

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

    // CalendarOfEventsView
    @GetMapping(value = "/about/CalendarOfEvents")
    public String CalendarOfEventsView(Model model, @RequestParam(defaultValue = "0") int page){
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
        return "about/event";
    }

    // 캘리던 및 이벤트 디테일 뷰
    @GetMapping(value = "/about/CalendarOfEvents/{eventId}")
    public String CalendarOfEventsDetailView(@PathVariable Long eventId, Model model){
        List<ProductCategoryDto> categories = categoryService.findAll();
        EventDTO EventDTO = aboutService.CalenderOfEventDetail(eventId);

        model.addAttribute("event", EventDTO);
        model.addAttribute("categories", categories);
        return "about/eventDetail";
    }

    // 켈린더 및 이벤트 추가 페이지
    @GetMapping(value = "/admin/CalendarOfEventsAdd")
    public String CalendarOfEventsAddView(EventDTO eventDTO,Model model){

        model.addAttribute("eventDTO", eventDTO);
        return "admin/eventForm";
    }
    // 켈린더 및 이벤트 어드민 리스트 페이지
    @GetMapping(value = "/admin/CalendarOfEvent")
    public String CalendarOfEventsListView(Model model){
        List<EventDTO> eventDTO = aboutService.CalendarOfEventList();

        model.addAttribute("eventDTO", eventDTO);
        return "admin/eventList";
    }

    // 캘린더 및 이벤트 수정 페이지
    @GetMapping(value = "/admin/event/{eventId}")
    public String CalenderOfEventsUpdateAdminPage(@PathVariable Long eventId, Model model) {

        EventDTO eventDTO = aboutService.CalenderOfEventDetail(eventId);

        model.addAttribute("eventDTO", eventDTO);
        return "admin/eventForm";
    }

    // 파트너 로고 수정 (어드민)
    @PostMapping(value = "/admin/event/{eventId}")
    public String CalenderOfEventsUpdate(@PathVariable String eventId, EventDTO eventDTO, Model model) {
        try {
            aboutService.CalendarOfEventUpdate(eventDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "행사일정 수정 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 캘린더 및 이벤트 추가
    @PostMapping(value = "/admin/CalendarOfEventsAdd")
    public String CalendarOfEventsAdd(EventDTO eventDTO,Model model) {
        try {
            aboutService.CalendarOfEventsAdd(eventDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이벤트 등록 중 에러가 발생하였습니다.");
            return "admin/eventForm";
        }
        return "redirect:/admin";
    }
    @PostMapping(value = "/admin/event/delete/{eventId}")
    public String partnersDelete(@PathVariable Long eventId, Model model) {
        try {
            aboutService.CalendarOfEventDelete(eventId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "행사일정 삭제 중 에러가 발생하였습니다.");
        }
        return "redirect:/admin";
    }

    // 파트너 로고 생성 페이지
    @GetMapping(value = "/admin/partnerAdd")
    public String partnersAdminPage(PartnersDTO partnersDTO, Model model) {

        model.addAttribute("partnersDTO", partnersDTO);
        return "partner/partnersForm";
    }
    // 파트너 로고 리스트
    @GetMapping(value = "/admin/partners")
    public String partnerListAdminPage(Model model) {

        List<PartnersDTO> partnersDTO = aboutService.partnerView();

        model.addAttribute("partnersDTO", partnersDTO);
        return "partner/partnerList";
    }

    // 파트너 로고 생성
    @PostMapping(value = "/admin/partners/new")
    public String partnersAdd(@RequestPart(name = "Img") MultipartFile partnerImg, Model model) throws Exception {
        try {
            aboutService.partnerAdd(partnerImg);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "로고 등록 중 에러가 발생하였습니다.");
            return "partner/partnersForm";
        }
        return "redirect:/admin";
    }

    // 파트너 로고 수정 페이지
    @GetMapping(value = "/admin/partners/{partnersId}")
    public String partnersUpdateAdminPage(@PathVariable Long partnersId, Model model) {

        PartnersDTO partners = aboutService.findByPartnersId(partnersId);

        model.addAttribute("partnersDTO", partners);
        return "partner/partnersForm";
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
