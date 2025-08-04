package com.wonjjong.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("won.dev@splearn.app", "wonjjong", "passwordHash");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("constructor null check")
    public void constructorNullCheck(){
        //given
        assertThatThrownBy(() -> new Member(null, null, null))
                .isInstanceOf(NullPointerException.class);

        //when

        //then
    }

    @Test
    @DisplayName("activate")
    void activate() {
        //given
        Member member = new Member("won.dev", "won.dev", "secret");

        //when
        member.activate();

        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("activate fail")
    void activateFail() {
        //given
        Member member = new Member("won.dev", "won.dev", "secret");

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
        Member member = new Member("won.dev", "won.dev","secret");
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
        Member member = new Member("won.dev", "won.dev", "secret");

        //when
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
        member.activate();
        member.deactivate();

        //then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

}