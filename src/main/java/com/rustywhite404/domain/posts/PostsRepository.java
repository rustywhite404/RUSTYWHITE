package com.rustywhite404.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    // DAO의 역할을 함
    // Entity 클래스와 함께 존재해야 함.

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") //SpringDataJpa에서 제공하지 않는 메소드는 이렇게 쿼리로 작성해도 됨.
    List<Posts> findAllDesc();

}
