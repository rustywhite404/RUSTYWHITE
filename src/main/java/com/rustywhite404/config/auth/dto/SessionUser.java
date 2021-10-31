package com.rustywhite404.config.auth.dto;

import com.rustywhite404.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    //SessionUser에서는 인증된 사용자 정보만 필요하다. 그 외의 정보는 필요없으니 name, email, picture만 필드로 선언한 것.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
