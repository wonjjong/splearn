package com.wonjjong.splearn.application.member.provided;

import com.wonjjong.splearn.SplearnTestConfiguration;
import com.wonjjong.splearn.domain.member.Member;
import com.wonjjong.splearn.domain.member.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    @DisplayName("find")
    void find() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member find = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(find.getId());
    }


}