package com.learning.socialmediablog.social_media_blog_app.service;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    //get all comments by id
    List<CommentDto> getAllCommentsByPostId(long postId);


    CommentDto getCommentByPostIdAndCommentId(long postId, long commentId);

    //create comments
    CommentDto createCommentForPost(long postId, CommentDto commentDto);

    //update comments
    CommentDto updateCommentByCommentByPostIdAndCommentId(long postId, long commentId, CommentDto commentDto);

    //delete particular comment on particular post
    String deleteCommentByPostIdCommentId(long postId, long commentId);

    //delete all comments on particular post
    String deleteAllCommentsOfPostsFromPostId(long postId);


}
