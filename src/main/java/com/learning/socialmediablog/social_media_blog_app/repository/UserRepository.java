package com.learning.socialmediablog.social_media_blog_app.repository;

import com.learning.socialmediablog.social_media_blog_app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //find by email
    Optional<UserEntity> findByEmail(String email);

    //find by username
    Optional<UserEntity> findByUsername(String username);

    //find by username or email
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

}
