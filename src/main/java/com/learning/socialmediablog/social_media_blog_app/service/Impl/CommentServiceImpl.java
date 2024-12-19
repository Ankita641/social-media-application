package com.learning.socialmediablog.social_media_blog_app.service.Impl;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.entity.CommentEntity;
import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import com.learning.socialmediablog.social_media_blog_app.exception.ResourceNotFoundException;
import com.learning.socialmediablog.social_media_blog_app.repository.CommentRepository;
import com.learning.socialmediablog.social_media_blog_app.repository.PostRepository;
import com.learning.socialmediablog.social_media_blog_app.service.CommentService;
import com.learning.socialmediablog.social_media_blog_app.utils.CommentEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentEntityMapper commentEntityMapper;

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        //this method(findByPostId) is not present in jpa repository that why we need to define new custom method in CommentRepository. spring JPA provide an implementation we just only define method there.
        List<CommentEntity> commentEntitiesList = this.commentRepository.findByPostId(postId);
        if (commentEntitiesList != null && !commentEntitiesList.isEmpty()){
            return commentEntitiesList.stream().map(commentEntity -> this.commentEntityMapper.convertEntityToDto(commentEntity)).toList();
        }

        return null;
    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long commentId) {

        //validate or fetch post entity details from post table of DB
        PostEntity postEntity = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post id not Found :: " + postId));

        //fetch comment by comment id
        CommentEntity commentEntity = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment id not Found :: " + commentId));

        //validate the particular comment belong to that post
        if (commentEntity != null && postEntity != null) {
            if (!Objects.equals(commentEntity.getPostEntity().getId(), postEntity.getId())) {
                throw new RuntimeException("Bad Request :: Comment Not Found");
            }else {
                return this.commentEntityMapper.convertEntityToDto(commentEntity);
            }
        }
       throw new RuntimeException("Bad Request");
    }

    @Override
    public CommentDto createCommentForPost(long postId, CommentDto commentDto) {

        //Convert CommentDto to CommentEntity
        CommentEntity commentEntity = this.commentEntityMapper.convertDtoToEntity(commentDto);

        //fetch Post Entity from post table using postId
        PostEntity postEntity = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post id not Found :: " + postId));

        //Attach or set comment to particular or associated Post Entity
        commentEntity.setPostEntity(postEntity);

        //Save Comment Entity
        CommentEntity newlySavedCommentEntity = this.commentRepository.save(commentEntity);

        //Map Comment Entity to Comment DTO and return newly create Comment DTO
        return this.commentEntityMapper.convertEntityToDto(newlySavedCommentEntity);
    }

    @Override
    public CommentDto updateCommentByCommentByPostIdAndCommentId(long postId, long commentId, CommentDto commentDto) {

        //Fetch Post Entity using Post Repository from PostId
        PostEntity postEntity = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post id not Found :: " + postId));

        //fetch comment Entity using Comment Repository from commentId
        CommentEntity commentEntity = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment id not Found :: " + commentId));

        //validate comment belong to that particular post
        if (commentEntity != null && postEntity != null) {
            if (!Objects.equals(commentEntity.getPostEntity().getId(), postEntity.getId())) {
                throw new RuntimeException("Bad Request :: Comment Not Found");
            }
        }

        //if valid then Update old Comment details with new CommentDto
        if (commentEntity != null && commentDto != null) {
            commentEntity.setEmail(commentDto.getEmail());
            commentEntity.setUsername(commentDto.getUsername());
            commentEntity.setBody(commentDto.getBody());
        }

        //save updated comment entity to db
        CommentEntity newlySavedCommentEntity = this.commentRepository.save(commentEntity);

        return this.commentEntityMapper.convertEntityToDto(newlySavedCommentEntity);
    }

    @Override
    public String deleteCommentByPostIdCommentId(long postId, long commentId) {

        //Fetch Post Entity using Post Repository from PostId
        PostEntity postEntity = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post id not Found :: " + postId));

        //fetch comment Entity using Comment Repository from commentId
        CommentEntity commentEntity = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment id not Found :: " + commentId));

        //validate comment belong to that particular post
        if (commentEntity != null && postEntity != null) {
            if (!Objects.equals(commentEntity.getPostEntity().getId(), postEntity.getId())) {
                throw new RuntimeException("Bad Request :: Comment Not Found");
            }
        }

        this.commentRepository.delete(commentEntity);
        return "Successfully Deleted Comment : " + commentId;
    }

    @Override
    public String deleteAllCommentsOfPostsFromPostId(long postId) {

        this.commentRepository.deleteByPostId(postId);
        return "Successfully Deleted Comment for Post : " + postId;
    }
}
