package com.aidharbor.DTO;

import com.aidharbor.Entity.BaseEntity;
import com.aidharbor.Entity.Enum.EventCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO extends BaseEntity {

    private Long id;
    private String title;
    private String titleImgUrl;
    private String content;
    private EventCategory eventCategory;
}
