package com.learning.socialmediablog.social_media_blog_app.repository;

import com.learning.socialmediablog.social_media_blog_app.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
