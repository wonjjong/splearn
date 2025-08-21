package com.wonjjong.splearn.application.member.required;

import com.wonjjong.splearn.domain.member.Profile;
import com.wonjjong.splearn.domain.shared.Email;
import com.wonjjong.splearn.domain.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/*
* 회원 정보를 저장하거나 조회한다
*/
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("select m from Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}
