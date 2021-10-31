package com.rustywhite404.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반사용자");
    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 붙어야만 함

    private final String key;
    private final String title;

}
