package com.learning.socialmediablog.social_media_blog_app.repository;

import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
