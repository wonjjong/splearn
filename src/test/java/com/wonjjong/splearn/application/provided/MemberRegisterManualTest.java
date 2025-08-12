package com.wonjjong.splearn.application.provided;

import com.wonjjong.splearn.application.MemberService;
import com.wonjjong.splearn.application.required.EmailSender;
import com.wonjjong.splearn.application.required.MemberRepository;
import com.wonjjong.splearn.domain.Email;
import com.wonjjong.splearn.domain.Member;
import com.wonjjong.splearn.domain.MemberFixture;
import com.wonjjong.splearn.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {
    @Test
    @DisplayName("register test stub")
    void registerTestStub() {
        MemberRegister memberRegister = new MemberService(
                new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

    }

    @Test
    @DisplayName("register test mock")
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegister memberRegister = new MemberService(
                new MemberRepositoryStub(), emailSenderMock , MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("register test mockito")
    void registerTestMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);
        MemberRegister memberRegister = new MemberService(
                new MemberRepositoryStub(), emailSenderMock , MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());

    }


    static class MemberRepositoryStub implements MemberRepository {

        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        public List<Email> getTos() {
            return tos;
        }

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }
}