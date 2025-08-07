package com.wonjjong.splearn.domain;


public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("won.dev@splearn.app");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "wonjjong", "password");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password + "Encoded";
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return (password + "Encoded").equals(passwordHash);
            }
        };
    }


}
