package com.aidharbor.Service;

import com.aidharbor.DTO.ContactDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;


    // 메일 양식 작성
    public MimeMessage createEmailForm(ContactDTO contactDTO) throws MessagingException, UnsupportedEncodingException, MessagingException {
        String setFrom = "gma78240@gmail.com";
        String toEmail = contactDTO.getEmail();
        String title = "Aidharbor 문의 요청";

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail="";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> Aidharbor 문의 내역입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p> 이름 : "+ contactDTO.getUserName() + "<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p> 이메일 : "+ contactDTO.getEmail() + "<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p> 휴대폰 : "+ contactDTO.getPhoneNumber() + "<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p> 제목 : "+ contactDTO.getTitle() + "<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p> 내용 : "+ contactDTO.getContent() + "<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "</div>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(ContactDTO contactDTO) throws MessagingException, UnsupportedEncodingException, MessagingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(contactDTO);
        //실제 메일 전송
        javaMailSender.send(emailForm);

        return null;
    }
}

