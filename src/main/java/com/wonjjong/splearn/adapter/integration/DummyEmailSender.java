package com.wonjjong.splearn.adapter.integration;

import com.wonjjong.splearn.application.member.required.EmailSender;
import com.wonjjong.splearn.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback // spring 6.2 version
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email : " + email);
    }
}
