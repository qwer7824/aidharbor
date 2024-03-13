package com.aidharbor.DTO;


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
    private String userName;

    @Email
    @NotNull(message = "Email Check")
    private String email;

    @NotNull(message = "PhoneNumber Check")
    private String phoneNumber;

    @NotBlank(message = "Title Check")
    private String title;

    @NotBlank(message = "content Check")
    private String content;
}
