package com.aidharbor.Entity;

import com.aidharbor.DTO.EventDTO;
import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.Enum.EventCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;
    private String UsTitle;

    @Lob
    @Column(columnDefinition="MEDIUMTEXT")
    private String content;

    @Lob
    @Column(columnDefinition="MEDIUMTEXT")
    private String UsContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory eventCategory;


    public void updateEvent(EventDTO eventDTO) {
        this.title = eventDTO.getTitle();
        this.UsTitle = eventDTO.getUsTitle();
        this.content = eventDTO.getContent();
        this.UsContent = eventDTO.getUsContent();
        this.eventCategory = eventDTO.getEventCategory();
    }
}
