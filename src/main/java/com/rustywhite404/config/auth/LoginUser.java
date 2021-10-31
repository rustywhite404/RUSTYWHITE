package com.rustywhite404.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //이 어노테이션이 생성될 수 있는 위치. 지금은 메소드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME) // 어느 시점까지 어노테이션의 메모리를 가져갈 지 설정. (RUNTIME중에 어노테이션의 정보를 쓸 수 있도록 설정)
public @interface LoginUser {
    // @interface 이렇게 쓰면 이 파일이 어노테이션 클래스가 됨.
    // LoginUser라는 어노테이션이 생성되었다고 보면 됨.


}
