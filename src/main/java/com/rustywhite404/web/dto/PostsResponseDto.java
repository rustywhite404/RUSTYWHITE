package com.rustywhite404.web.dto;

import com.rustywhite404.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity){

        //Entity 필드의 일부만 사용하므로 생성자로 Entity를 받아 필드에 값을 넣는다.
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
