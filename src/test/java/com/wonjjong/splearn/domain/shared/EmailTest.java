package com.wonjjong.splearn.domain.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {
    @Test
    @DisplayName("equality")
    void equality() {
        var email1 = new Email("wonjjong.dev@splearn.app");
        var email2 = new Email("wonjjong.dev@splearn.app");

        // record는 equals를 정의해줌
        assertThat(email1).isEqualTo(email2);

    }

}