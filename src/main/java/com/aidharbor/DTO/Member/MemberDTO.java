package com.aidharbor.DTO.Member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
    @NotBlank(message = "Username Check")
    private String username;
    @NotBlank(message = "Password Check")
    private String password;
}
