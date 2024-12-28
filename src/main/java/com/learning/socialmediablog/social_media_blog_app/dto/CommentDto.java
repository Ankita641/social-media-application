package com.learning.socialmediablog.social_media_blog_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 3, message = "UserName should have at least 3 Characters")
    private String username;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 7, message = "Comment Body should have at least 7 Characters")
    private String body;
}
