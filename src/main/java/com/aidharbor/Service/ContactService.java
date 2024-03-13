package com.aidharbor.Service;

import com.aidharbor.DTO.ContactDTO;
import com.aidharbor.Entity.Contact;
import com.aidharbor.Repository.ContactRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final MailService mailService;

    @Transactional
    public String contactAdd(ContactDTO contactDTO) throws MessagingException, UnsupportedEncodingException {

        Contact contact = Contact.contactAdd(contactDTO);

        mailService.sendEmail(contactDTO);

        contactRepository.save(contact);

       return contactDTO.getUserName();
    }
}
