package com.learning.socialmediablog.social_media_blog_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private int status;

}
