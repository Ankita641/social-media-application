package com.learning.socialmediablog.social_media_blog_app.utils;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostEntityMapper {

    @Autowired
    private ModelMapper modelMapper;

    //map or convert post entity to post
    public PostDto mapPostEntityToPostDto(final PostEntity postEntity){
//        PostDto postDto = new PostDto();
//        postDto.setId(postEntity.getId());
//        postDto.setTitle(postEntity.getTitle());
//        postDto.setDescription(postEntity.getDescription());
//        postDto.setContent(postEntity.getContent());
//        return postDto;
        return this.modelMapper.map(postEntity, PostDto.class);

    }


    //map or convert post dto to post entity
    public PostEntity mapPostDtoToPostEntity(final PostDto postDto){
/*        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        return postEntity;*/
        return this.modelMapper.map(postDto, PostEntity.class);
    }
}
