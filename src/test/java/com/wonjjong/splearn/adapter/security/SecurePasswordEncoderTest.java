package com.wonjjong.splearn.adapter.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {
    @Test
    @DisplayName("secure password encoder")
    void securePasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String passwordHash = securePasswordEncoder.encode("password");

        assertThat(securePasswordEncoder.matches("password", passwordHash)).isTrue();
        assertThat(securePasswordEncoder.matches("wrong", passwordHash)).isFalse();
    }

}