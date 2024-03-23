package com.aidharbor.Entity;

import com.aidharbor.DTO.PartnersDTO;
import com.aidharbor.DTO.Product.ProductDTO;
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
public class Partners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    @Column(length = 500)
    private String partnerImg;

    public void updatePartners(String partnerImg) {
        this.partnerImg = partnerImg;
    }
}
