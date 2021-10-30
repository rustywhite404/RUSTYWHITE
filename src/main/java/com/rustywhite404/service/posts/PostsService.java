package com.rustywhite404.service.posts;

import com.rustywhite404.domain.posts.Posts;
import com.rustywhite404.domain.posts.PostsRepository;
import com.rustywhite404.web.dto.PostsResponseDto;
import com.rustywhite404.web.dto.PostsSaveRequestDto;
import com.rustywhite404.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    //LOMBOK의 RequiredArgsConstructor이 final이 선언된 모든 필드를 인자값으로 하는 생성자를 만들어줬기 때문에 Autowired를 쓸 필요가 없음(권장하지 않음)

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }
}
