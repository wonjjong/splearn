package com.wonjjong.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    PasswordEncoder passwordEncoder;
    Member member;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password + "Encoded";
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return (password + "Encoded").equals(passwordHash);
            }
        };

        member = Member.create(new MemberCreateRequest("won.dev@splearn.app", "wonjjong", "password"), passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("activate")
    void activate() {
        //given

        //when
        member.activate();

        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
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
                Member.create(new MemberCreateRequest("invalid email", "wonjjong", "password"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("wonjjong.dev@gmail.com", "wonjjong", "password"), passwordEncoder);
        //when

        //then
    }

}