package com.wonjjong.splearn.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {
    @Test
    @DisplayName("profile")
    void profile() {
        new Profile("wonjjong");
        new Profile("wonjjong1");
        new Profile("12345");
    }

    @Test
    @DisplayName("profile fail")
    void profileFail() {
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("url")
    void url() {
        Profile profile = new Profile("wonjjong");

        assertThat(profile.url()).isEqualTo("@wonjjong");
    }


}