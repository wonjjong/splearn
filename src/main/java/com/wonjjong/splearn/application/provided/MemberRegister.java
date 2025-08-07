package com.wonjjong.splearn.application.provided;

import com.wonjjong.splearn.domain.Member;
import com.wonjjong.splearn.domain.MemberRegisterRequest;

/*
* 회원의 등록과 관련된 기능을 제공한다
* */
public interface MemberRegister {
    // 토비님은 어댑터 쪽으로 entity를 반환하는것이 아무런 문제가 되지 않는다고 얘기함
    // 프레젠테이션 레이어에서는 도메인의 정보를 받아서 이걸 가지고 클라이언트한에 응답하는 결과를 다시 생성하는 것
    Member register(MemberRegisterRequest registerRequest);
}
