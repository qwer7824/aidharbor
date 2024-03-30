package com.aidharbor.DTO.Contact;


import com.aidharbor.DTO.Product.ProductDTO;
import com.aidharbor.Entity.BaseEntity;
import com.aidharbor.Entity.Contact;
import com.aidharbor.Entity.Enum.ContactState;
import com.aidharbor.Entity.Product;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO extends BaseEntity {

    private Long id;

    @NotBlank(message = "Name Check")
    private String firstName;
    @NotBlank(message = "Name Check")
    private String lastName;

    @Email
    @NotNull(message = "Email Check")
    private String userEmail;

    @NotBlank(message = "content Check")
    private String message;

    private ContactState contactState;

    private static ModelMapper modelMapper = new ModelMapper();
    // Entity -> DTO
    public static ContactDTO of(Contact contact) {
        return modelMapper.map(contact,ContactDTO.class);
    }

}
