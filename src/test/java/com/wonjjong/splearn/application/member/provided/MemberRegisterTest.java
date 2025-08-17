package com.wonjjong.splearn.application.member.provided;

import com.wonjjong.splearn.SplearnTestConfiguration;
import com.wonjjong.splearn.domain.member.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 특별한 이유가 있어서 매뉴얼 테스트를 만들 수는 있지만 대부분의 경우에는 스프링 컨테이너를 띄우는 방식의
 * 테스트를 주로 사용함. 같은 스프링 컨테이너를 사용하는 테스트끼리는 스프링 컨테이너를 공유하기 때문에
 * 속도가 크게 차이는 안남
 */
@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    @DisplayName("register")
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("duplicate email fail")
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("activate")
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    @DisplayName("deactivate")
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    @DisplayName("updateInfo")
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("wonjjong", "wonjjong", "자기소개");
        member = memberRegister.updateInfo(member.getId(), request);

        assertThat(member.getDetail().getProfile().address()).isEqualTo("wonjjong");
    }


    @Test
    @DisplayName("memberRegisterRequest fail")
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("wonjjong.dev@gmail.com", "won", "longsecret"));
        checkValidation(new MemberRegisterRequest("wonjjong.dev@gmail.com", "wonjjong_____________________________________", "longsecret"));
        checkValidation(new MemberRegisterRequest("wonjjong.devgmail.com", "won", "longsecret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
            .isInstanceOf(ConstraintViolationException.class);
    }


}
