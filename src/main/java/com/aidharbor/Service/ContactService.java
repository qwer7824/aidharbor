package com.aidharbor.Service;

import com.aidharbor.DTO.Contact.ContactDTO;
import com.aidharbor.Entity.Contact;
import com.aidharbor.Entity.Enum.ContactState;
import com.aidharbor.Repository.ContactRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    @Transactional
    public String contactAdd(ContactDTO contactDTO) throws MessagingException, UnsupportedEncodingException {

        Contact contact = Contact.contactAdd(contactDTO);

        mailService.sendEmail(contactDTO);

        contactRepository.save(contact);

       return contactDTO.getUserEmail();
    }

    public List<ContactDTO> findAll() {
        List<Contact> contacts = contactRepository.findAll();

        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .sorted(Comparator.comparing(ContactDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public ContactDTO findById(Long contactId){
        Contact contact = contactRepository.findById(contactId).orElseThrow(null);

        if(contact.getContactState() == ContactState.NEW) {
            contact.contactHOLD();
            contactRepository.save(contact);
        }

        return ContactDTO.of(contact);
    }

    public void contactCheck(Long contactId){
        Contact contact = contactRepository.findById(contactId).orElseThrow(null);

        if(!(contact.getContactState() == ContactState.CHECK)) {
            contact.contactCHECK();
            contactRepository.save(contact);
        }
    }

}
