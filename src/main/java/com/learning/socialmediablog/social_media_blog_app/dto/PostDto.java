package com.learning.socialmediablog.social_media_blog_app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

//this is pojo class. this we will use for transfer the data/resource one layer to another layer. this is exactly proxy of postEntity.
@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 3, message = "Post Title should have at least 3 Characters")
    private String title;

    @NotEmpty
    @Size(min = 5, message = "Post Description should have at least 5 Characters")
    private String description;

    @NotEmpty
    @Size(min = 7, message = "Post Content should have at least 7 Characters")
    private String content;
}
