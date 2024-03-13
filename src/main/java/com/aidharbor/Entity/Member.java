package com.aidharbor.Entity;

import com.aidharbor.DTO.Member.MemberDTO;
import com.aidharbor.Entity.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role roles;

    public static Member memberSign(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(memberDTO.getUsername())
                .password(passwordEncoder.encode(memberDTO.getPassword()))  //암호화처리
                .roles(Role.USER)
                .build();
    }

}
