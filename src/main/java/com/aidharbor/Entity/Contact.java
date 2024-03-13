package com.aidharbor.Entity;

import com.aidharbor.DTO.ContactDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    private String userName;

    private String email;

    private String phoneNumber;

    private String title;

    @Column(columnDefinition="MEDIUMTEXT")
    private String content;

    public static Contact contactAdd(ContactDTO contactDTO) {
        return Contact.builder()
                .userName(contactDTO.getUserName())
                .email(contactDTO.getEmail())  //암호화처리
                .phoneNumber(contactDTO.getPhoneNumber())
                .title(contactDTO.getTitle())
                .content(contactDTO.getContent())
                .build();
    }
}
