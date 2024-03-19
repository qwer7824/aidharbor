package com.aidharbor.DTO.Contact;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    @NotBlank(message = "Name Check")
    private String firstName;
    @NotBlank(message = "Name Check")
    private String lastName;

    @Email
    @NotNull(message = "Email Check")
    private String userEmail;

    @NotBlank(message = "content Check")
    private String message;
}
