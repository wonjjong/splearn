package com.wonjjong.splearn.application.provided;

import com.wonjjong.splearn.domain.Member;

/**
 * 회원을 조회한다.
 * */
public interface MemberFinder {
    Member find(Long memberId);
}
