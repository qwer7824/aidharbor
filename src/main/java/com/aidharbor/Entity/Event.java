package com.aidharbor.Entity;

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
    @Lob
    @Column(columnDefinition="MEDIUMTEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory eventCategory;
}
