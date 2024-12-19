package com.learning.socialmediablog.social_media_blog_app.utils;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//we just remove ugly code to replace with ModelMapper to add dependency as well

@Component
public class CommentEntityMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CommentDto convertEntityToDto(CommentEntity commentEntity) {
        if (commentEntity != null){
//            CommentDto commentDto = new CommentDto();
//            commentDto.setId(commentEntity.getId());
//            commentDto.setUsername(commentEntity.getUsername());
//            commentDto.setEmail(commentEntity.getEmail());
//            commentDto.setBody(commentEntity.getBody());
//              return commentDto;

            //using modelMapper
          //  CommentDto commentDto = this.modelMapper.map(commentEntity, CommentDto.class);

          //in simple way or shortcut way to using modelMapper
        return this.modelMapper.map(commentEntity, CommentDto.class);
    }
    return null;
    }


    public CommentEntity convertDtoToEntity(CommentDto commentDto) {
        if (commentDto != null){
//            CommentEntity commentEntity = new CommentEntity();
//            commentEntity.setUsername(commentDto.getUsername());
//            commentEntity.setEmail(commentDto.getEmail());
//            commentEntity.setBody(commentDto.getBody());
//            return commentEntity;

            return this.modelMapper.map(commentDto, CommentEntity.class);
        }
        return null;
    }
}
