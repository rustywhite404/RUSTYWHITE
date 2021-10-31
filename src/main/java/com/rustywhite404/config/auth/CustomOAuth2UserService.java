package com.rustywhite404.config.auth;

import com.rustywhite404.config.auth.dto.OAuthAttributes;
import com.rustywhite404.config.auth.dto.SessionUser;
import com.rustywhite404.domain.user.User;
import com.rustywhite404.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    // 구글 로그인 이후 가져온 사용자의 정보(email, name, picture) 들을 기반으로 가입 및 정보수정, 세션 저장을 지원

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registerationId = userRequest.getClientRegistration().getRegistrationId(); //현재 로그인 중인 서비스를 구분하는 코드. 여러 소셜로그인 연동 시에 어느 사이트에서 로그인 한건지 구분하기 위해 필요함
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2 로그인 진행 시 키가 되는 필드값. PK와 같은 의미
        //구글은 기본적으로 코드를 지원하지만, 네이버와 카카오 등은 기본 지원하지 않는다. 이후 네이버, 구글로그인을 함께 지원할 때 사용함.
        OAuthAttributes attributes = OAuthAttributes.of(registerationId, userNameAttributeName, oAuth2User.getAttributes());
        //OAuthAttributes: OAuth2UserService를 통해 가져온 attribute를 담을 클래스.
        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));
        //세션에 사용자 정보를 저장하기 위한 Dto

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
