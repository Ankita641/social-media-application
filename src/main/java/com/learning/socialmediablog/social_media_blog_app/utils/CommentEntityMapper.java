package com.learning.socialmediablog.social_media_blog_app.utils;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentEntityMapper {

    public CommentDto convertEntityToDto(CommentEntity commentEntity) {
        if (commentEntity != null){
            CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setUsername(commentEntity.getUsername());
        commentDto.setEmail(commentEntity.getEmail());
        commentDto.setBody(commentEntity.getBody());
        return commentDto;
    }
    return null;
    }


    public CommentEntity convertDtoToEntity(CommentDto commentDto) {
        if (commentDto != null){
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUsername(commentDto.getUsername());
            commentEntity.setEmail(commentDto.getEmail());
            commentEntity.setBody(commentDto.getBody());
            return commentEntity;
        }
        return null;
    }
}
