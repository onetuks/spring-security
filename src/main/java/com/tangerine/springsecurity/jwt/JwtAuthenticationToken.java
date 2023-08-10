package com.tangerine.springsecurity.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.StringJoiner;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private String credentials;

    public JwtAuthenticationToken(String principal, String credentials) {
        // 아직 인증되지 않은 상태
        super(null);
        super.setAuthenticated(false);

        this.principal = principal;
        this.credentials = credentials;
    }

    JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, String credentials) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // authentication 인증 상태과 완료되었음은 오로지 생성자를 이용한 방법만 가능하도록 하기 위해서
        // setter에서 true 권한 갱신이 들어오는 경우 예외 던저도록 구현
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - user constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JwtAuthenticationToken.class.getSimpleName() + "[", "]")
                .add("principal=" + principal)
                .add("credentials='" + credentials + "'")
                .toString();
    }
}