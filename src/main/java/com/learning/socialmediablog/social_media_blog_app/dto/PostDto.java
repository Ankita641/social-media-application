package com.learning.socialmediablog.social_media_blog_app.dto;

import lombok.Data;

//this is pojo class. this we will use for transfer the data/resource one layer to another layer. this is exactly proxy of postEntity.
@Data
public class PostDto {

    private long id;
    private String title;
    private String description;
    private String content;
}
