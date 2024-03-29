package com.aidharbor.DTO;

import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.BaseEntity;
import com.aidharbor.Entity.Enum.EventCategory;
import com.aidharbor.Entity.Event;
import com.aidharbor.Entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class EventDTO extends BaseEntity {

    private Long id;
    private String title;
    private String titleImgUrl;
    private String content;
    private EventCategory eventCategory;

    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static EventDTO of(Event event) {
        return modelMapper.map(event,EventDTO.class);
    }
}
