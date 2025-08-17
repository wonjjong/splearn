package com.wonjjong.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wonjjong.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static com.wonjjong.splearn.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    PasswordEncoder passwordEncoder;
    Member member;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();

        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    @DisplayName("activate")
    void activate() {
        assertThat(member.getDetail().getActivatedAt()).isNull();

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    @DisplayName("activate fail")
    void activateFail() {
        //given

        //when
        member.activate();

        //then
        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("deactivate")
    void deactivate() {
        //given
        member.activate();

        //when
        member.deactivate();

        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();

    }

    @Test
    @DisplayName("deactivate fail")
    void deactivateFail() {
        //given

        //when
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
        member.activate();
        member.deactivate();

        //then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("verify password")
    void verifyPassword() {
        //given

        //when

        //then
        assertThat(member.verifyPassword("password", passwordEncoder)).isEqualTo(true);
        assertThat(member.verifyPassword("hello", passwordEncoder)).isEqualTo(false);
    }

    @Test
    @DisplayName("change nickname")
    void changeNickname() {
        //given

        //when

        //then
        assertThat(member.getNickname()).isEqualTo("wonjjong");
        member.changeNickname("bomi");
        assertThat(member.getNickname()).isEqualTo("bomi");

    }

    @Test
    @DisplayName("change password")
    void changePassword() {
        //given


        //when
        member.changePassword("newPassword", passwordEncoder);

        //then
        assertThat(member.verifyPassword("newPassword", passwordEncoder)).isEqualTo(true);
    }

    @Test
    @DisplayName("should be active")
    void shouldBeActive() {
        //given
        assertThat(member.isActive()).isFalse();

        //when
        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();
        assertThat(member.isActive()).isFalse();

        //then
    }

    @Test
    @DisplayName("invalid email")
    void invalieEmail() {
        //given
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
        //when

        //then
    }

    @Test
    @DisplayName("update info")
    void updateInfo() {
        //given
        member.activate();

        //when
        var request = new MemberInfoUpdateRequest("leo", "wonjjong", "자기소개");
        member.updateInfo(request);

        //then
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

}