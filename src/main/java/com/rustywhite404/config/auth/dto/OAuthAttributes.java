package com.rustywhite404.config.auth.dto;

import com.rustywhite404.domain.user.Role;
import com.rustywhite404.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    //OAuth2UserService를 통해 가져온 0Auth2User의 attribute를 담을 클래스.
    // 구글, 네이버 등 모든 소셜 로그인을 이 클래스에서 사용.

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
        //of() : OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해주어야 함.
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder().name((String)attributes.get("name")).email((String)attributes.get("email")).picture((String)attributes.get("picture"))
                .attributes(attributes).nameAttributeKey(userNameAttributeName).build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        return OAuthAttributes.builder().name((String)response.get("name")).email((String)response.get("email")).picture((String)response.get("profile_image")).attributes(response).nameAttributeKey(userNameAttributeName).build();
    }

    public User toEntity(){
        return User.builder().name(name).email(email).picture(picture).role(Role.GUEST).build();
        // 최초 가입 시에 엔티티를 생성함.
        // 가입할 때의 기본 권한을 GUEST로 주기로 함.
    }

}
