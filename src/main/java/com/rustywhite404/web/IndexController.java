package com.rustywhite404.web;

import com.rustywhite404.config.auth.dto.SessionUser;
import com.rustywhite404.service.posts.PostsService;
import com.rustywhite404.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());

        SessionUser user = (SessionUser)httpSession.getAttribute("user");
        //CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성해놨다.
        // 그래서 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있는 것.

        if(user!=null){
            model.addAttribute("googleName",user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; //posts/save를 호출하면 posts-save.mustache를 호출한다
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }

}
