package com.rustywhite404.config.auth;

import com.rustywhite404.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션 disable
           .and()
                .authorizeRequests() //URL별 관리를 설정하는 옵션의 시작점. 이게 선언되어야만 antMatcher 옵션 가용 가능
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll().antMatchers("/api/v1/**").hasRole(Role.USER.name())
                //andMatchers : 권한 관리 대상을 지정하는 옵션. URL, HTTP 메소드별로 관리 가능. 지금은 "/"등 지정된 url은 전체 열람 권한을 준 상태고, /api/vi/** 주소를 가진 API는 USER 권한에게만 권한을 줬음.
                .anyRequest().authenticated() //anyRequest : 설정된 값들 이외의 나머지 URL을 나타내며, 지금은 authenticated를 추가해 인증된(로그인 한) 사용자에게만 허용 가능하게 했음.
                .and()
                    .logout().logoutSuccessUrl("/") //로그아웃 기능에 대한 여러 설정의 진입점
                .and()
                    .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
                    //로그인 기능에 대한 설정 진입점.
                    //userInfoEndPoint : 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당.
                    // userService : 로그인 후 후속 조치를 진행할 UserService의 구현체를 등록.
                    // 소셜 서비스에서 사용자 정보를 가져온 상태에서 추가로 진행하려는 기능을 명시할 수 있음.

    }
}
