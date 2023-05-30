package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
    @Query("from Post a where a.title like %:search% or a.description like %:search%")
    List<Post> searchByTitleLikeOrDescriptionLike(@Param("search") String search);

    Post findById(long id);
}
