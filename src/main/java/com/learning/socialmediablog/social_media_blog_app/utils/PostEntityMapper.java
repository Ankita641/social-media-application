package com.learning.socialmediablog.social_media_blog_app.utils;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostEntityMapper {

    //map or convert post entity to post
    public PostDto mapPostEntityToPostDto(final PostEntity postEntity){
        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        postDto.setDescription(postEntity.getDescription());
        postDto.setContent(postEntity.getContent());
        return postDto;
    }


    //map or convert post dto to post entity
    public PostEntity mapPostDtoToPostEntity(final PostDto postDto){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }
}
