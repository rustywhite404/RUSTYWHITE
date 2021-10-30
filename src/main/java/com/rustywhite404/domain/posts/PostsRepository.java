package com.rustywhite404.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    // DAO의 역할을 함
    // Entity 클래스와 함께 존재해야 함.


}
