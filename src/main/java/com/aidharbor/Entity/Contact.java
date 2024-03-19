package com.aidharbor.Entity;

import com.aidharbor.DTO.Contact.ContactDTO;
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

    private String firstName;
    private String lastName;

    private String userEmail;

    @Column(columnDefinition="MEDIUMTEXT")
    private String message;

    public static Contact contactAdd(ContactDTO contactDTO) {
        return Contact.builder()
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .userEmail(contactDTO.getUserEmail())
                .message(contactDTO.getMessage())
                .build();
    }
}
