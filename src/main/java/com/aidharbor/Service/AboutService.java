package com.aidharbor.Service;

import com.aidharbor.DTO.Contact.ContactDTO;
import com.aidharbor.DTO.EventDTO;
import com.aidharbor.DTO.PartnersDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Event;
import com.aidharbor.Entity.Partners;
import com.aidharbor.Entity.Product;
import com.aidharbor.Repository.EventsRepository;
import com.aidharbor.Repository.PartnersRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutService {
    private final PartnersRepository partnersRepository;
    private final EventsRepository eventsRepository;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;
    private final ImgService imgService;

    @Transactional
    public void partnerAdd(MultipartFile partnerImg) throws IOException {
        String storedFileName = s3Uploader.upload(partnerImg, "images");

        Partners partners = Partners.builder()
                .partnerImg(storedFileName)
                .build();

        partnersRepository.save(partners);
    }

    public List<PartnersDTO> partnerView(){
        List<Partners> partners = partnersRepository.findAll();
        List<PartnersDTO> partnersDTO = new ArrayList<>();
        for(Partners partner : partners) {
            PartnersDTO dto = new PartnersDTO();
            dto.setId(partner.getId());
            dto.setPartnerImg(partner.getPartnerImg());
            partnersDTO.add(dto);
        }
        return partnersDTO;
    }

    public PartnersDTO findByPartnersId(Long partnersId) {
        Partners partners = partnersRepository.findById(partnersId).orElse(null);
        PartnersDTO dto = modelMapper.map(partners, PartnersDTO.class);
        return dto;
    }

    @Transactional
    public void partnerUpdate(PartnersDTO partnersDTO, MultipartFile img) throws IOException {

        Partners partners = partnersRepository.findById(partnersDTO.getId()).orElse(null);

        if (!img.isEmpty()) {
            String url = imgService.imgSubString(partners.getPartnerImg());
            s3Uploader.deleteImgFile(url);
            String storedFileName = s3Uploader.upload(img, "images");
            partners.updatePartners(storedFileName);
        }

        partnersRepository.save(partners);
    }


    @Transactional
    public void partnersDelete(Long partnersId) throws IOException {
        Partners partners = partnersRepository.findById(partnersId).orElseThrow(null);
        partnersRepository.delete(partners);
        imgService.imgDelete(partners.getPartnerImg());
    }

    public List<EventDTO> CalendarOfEventList() {
        List<Event> eventList = eventsRepository.findAll();
        List<EventDTO> eventDTOList = eventList.stream()
                .map(this::convertToEventDTO)
                .sorted(Comparator.comparing(EventDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return eventDTOList;
    }

    public EventDTO CalenderOfEventDetail(Long eventId){
        Event event = eventsRepository.findById(eventId).orElseThrow(null);

        EventDTO eventDTO = EventDTO.of(event);
        return eventDTO;
    }

    private EventDTO convertToEventDTO(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);

        String content = event.getContent();
        Document doc = Jsoup.parse(content);

        String textOnly = doc.text();
        eventDTO.setContent(textOnly);

        Element firstImg = doc.select("img").first();
        if (firstImg != null) {
            String titleImgUrl = firstImg.attr("src");
            eventDTO.setTitleImgUrl(titleImgUrl);
        } else {
            eventDTO.setTitleImgUrl(null); // 이미지가 없을 경우 null로 설정
        }

        return eventDTO;
    }
    @Transactional
    public void CalendarOfEventUpdate(EventDTO eventDTO) {

        Event event = eventsRepository.findById(eventDTO.getId()).orElseThrow(null);

        event.updateEvent(eventDTO);

        eventsRepository.save(event);
    }

    @Transactional
    // 상품 삭제
    public void CalendarOfEventDelete(Long eventId) throws IOException {
        Event event = eventsRepository.findById(eventId).orElseThrow(null);
        String content = event.getContent();
        eventsRepository.delete(event);
        imgService.DeleteConvert(content);
    }

    public void CalendarOfEventsAdd(EventDTO eventDTO) {

        Event event = Event.builder()
                .eventCategory(eventDTO.getEventCategory())
                .title(eventDTO.getTitle())
                .UsTitle(eventDTO.getUsTitle())
                .UsContent(eventDTO.getUsContent())
                .content(eventDTO.getContent())
                .build();

        eventsRepository.save(event);
    }

    public List<EventDTO> Top3List() {
        List<Event> events = eventsRepository.findTop3ByOrderByCreatedAtDesc();
        return events.stream()
                .map(this::convertToEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> findEventsByPage(int page, int size) {
        List<Event> eventList = eventsRepository.findAll();
        List<EventDTO> eventListDTO = eventList.stream()
                .map(this::convertToEventDTO)
                .sorted(Comparator.comparing(EventDTO::getCreatedAt).reversed())
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());

        return eventListDTO;
    }

    public long getTotalEventsCount() {
        return eventsRepository.count(); // 이벤트의 총 개수를 반환하는 메서드 호출
    }
}
