package com.learning.socialmediablog.social_media_blog_app.dto;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String username;
    private String email;
    private String body;
}
