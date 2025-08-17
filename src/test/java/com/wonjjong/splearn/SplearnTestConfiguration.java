package com.wonjjong.splearn;

import com.wonjjong.splearn.application.member.required.EmailSender;
import com.wonjjong.splearn.domain.member.MemberFixture;
import com.wonjjong.splearn.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

// 테스트 코드 쪽이 아닌 실제 어플리케이션쪽에 동일한 빈이 존재하더라도 테스트쪽에 설정한 빈이 우선 적용됨
@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {

        return (email, subject, body) -> {
            System.out.println("Email sent to: " + email);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
