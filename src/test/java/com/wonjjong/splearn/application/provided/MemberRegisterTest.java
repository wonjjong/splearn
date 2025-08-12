package com.wonjjong.splearn.application.provided;

import com.wonjjong.splearn.SplearnTestConfiguration;
import com.wonjjong.splearn.domain.DuplicateEmailException;
import com.wonjjong.splearn.domain.Member;
import com.wonjjong.splearn.domain.MemberFixture;
import com.wonjjong.splearn.domain.MemberStatus;
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
public record MemberRegisterTest(MemberRegister memberRegister) {

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
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }


}
