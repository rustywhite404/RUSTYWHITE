package com.rustywhite404.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //LOMBOK 기능, 기본 생성자 자동 추가
@Entity //테이블과 링크될 클래스임을 나타냄. 클래스의 카멜케이스 이름 = 스네이크(_) 테이블명으로 매칭함 (TextTable -> test_table)
public class Posts extends BaseTimeEntity{ //실제 DB의 테이블과 매칭될 클래스, Entity 클래스라고도 함.

    @Id //PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙. ~IDENTITY 옵션을 추가해야만 auto increament가 적용됨
    private Long id;

    //테이블 컬럼을 나타내지만 굳이 선언하지 않아도 이 클래스의 필드는 모두 컬럼이 됨.
    //굳이 사용하는 이유는 기본값 외에 추가로 변경하고 싶은 옵션이 있을 경우에 사용함(varchar 사이즈를 500으로 늘린다거나, 타입을 TEXT로 변경하거나...)
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //이 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함됨
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
