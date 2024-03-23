package com.aidharbor.Service;

import com.aidharbor.DTO.PartnersDTO;
import com.aidharbor.Entity.Partners;
import com.aidharbor.Entity.Product;
import com.aidharbor.Repository.PartnersRepository;
import jakarta.mail.Part;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AboutService {
    private final PartnersRepository partnersRepository;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;
    private final ImgService imgService;

    public void partnerAdd(MultipartFile partnerImg) throws IOException {
        String storedFileName = s3Uploader.upload(partnerImg, "images");

        Partners partners = Partners.builder()
                .partnerImg(storedFileName)
                .build();

        partnersRepository.save(partners);
    }

    public List<PartnersDTO> partnerView(){
        List<Partners> partners = partnersRepository.findAll();
        List<PartnersDTO> partnersDTO = new ArrayList<>();
        for(Partners partner : partners) {
            PartnersDTO dto = new PartnersDTO();
            dto.setId(partner.getId());
            dto.setPartnerImg(partner.getPartnerImg());
            partnersDTO.add(dto);
        }
        return partnersDTO;
    }

    public PartnersDTO findByPartnersId(Long partnersId) {
        Partners partners = partnersRepository.findById(partnersId).orElse(null);
        PartnersDTO dto = modelMapper.map(partners, PartnersDTO.class);
        return dto;
    }

    public void partnerUpdate(PartnersDTO partnersDTO, MultipartFile img) throws IOException {

        Partners partners = partnersRepository.findById(partnersDTO.getId()).orElse(null);

        if (!img.isEmpty()) {
            String url = imgService.imgSubString(partners.getPartnerImg());
            s3Uploader.deleteFile(url);
            String storedFileName = s3Uploader.upload(img, "images");
            partners.updatePartners(storedFileName);
        }

        partnersRepository.save(partners);
    }

    public void partnersDelete(Long partnersId) throws IOException {
        Partners partners = partnersRepository.findById(partnersId).orElseThrow(null);
        partnersRepository.delete(partners);
        imgService.imgLogoDelete(partners);
    }
}
