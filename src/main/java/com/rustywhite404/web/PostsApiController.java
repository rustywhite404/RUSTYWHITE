package com.rustywhite404.web;

import com.rustywhite404.service.posts.PostsService;
import com.rustywhite404.web.dto.PostsResponseDto;
import com.rustywhite404.web.dto.PostsSaveRequestDto;
import com.rustywhite404.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    //LOMBOK의 RequiredArgsConstructor이 final이 선언된 모든 필드를 인자값으로 하는 생성자를 만들어줬기 때문에 Autowired를 쓸 필요가 없음(권장하지 않음)

    @PostMapping("/api/v1/posts") // 글 등록 기능
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PostMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
