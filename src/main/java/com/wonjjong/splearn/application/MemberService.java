package com.wonjjong.splearn.application;

import com.wonjjong.splearn.application.provided.MemberRegister;
import com.wonjjong.splearn.application.required.EmailSender;
import com.wonjjong.splearn.application.required.MemberRepository;
import com.wonjjong.splearn.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.email())).isPresent())  {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }
}
