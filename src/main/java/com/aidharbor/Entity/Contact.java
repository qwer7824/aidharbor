package com.aidharbor.Entity;

import com.aidharbor.DTO.Contact.ContactDTO;
import com.aidharbor.Entity.Enum.ContactState;
import com.aidharbor.Entity.Enum.EventCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactState contactState;

    private String firstName;
    private String lastName;

    private String userEmail;

    @Column(columnDefinition="MEDIUMTEXT")
    private String message;

    public void contactHOLD() {
        this.contactState = ContactState.HOLD;
    }
    public void contactCHECK(){
        this.contactState = ContactState.CHECK;
    }



    public static Contact contactAdd(ContactDTO contactDTO) {
        return Contact.builder()
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .userEmail(contactDTO.getUserEmail())
                .message(contactDTO.getMessage())
                .contactState(ContactState.NEW)
                .build();
    }
}
