package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Difference between PathVariable and RequestParam ? : PathVariable has a thi particular path to map postId to postId in table.
// RequestParam is map name like /posts/{postId}/comments?name=ankita&location=us
//ResponseEntity is right to use ,send back to client, so status will be proper one

@RestController
@RequestMapping("/v1/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //Create Comment  - /posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto){
        CommentDto saveCommentDto = this.commentService.createCommentForPost(postId, commentDto);
        return new  ResponseEntity<>(saveCommentDto, HttpStatus.CREATED);
    }

    //get all Comments for Post Api - //posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable long postId){
      List<CommentDto> commentDtoList =  this.commentService.getAllCommentsByPostId(postId);
      return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    //get single Comment for Post api - /posts/{postId}/comments/{commentId}
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> fetchCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId){
      CommentDto commentDto = this.commentService.getCommentByPostIdAndCommentId(postId, commentId);
      return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //update comment rest api-   /posts/{postId}/comments/{commentId}
    //put replace the entire commentEntity
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public  ResponseEntity<CommentDto> updateCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentDto commentDto){
       CommentDto updatedCommentDto = this.commentService.updateCommentByCommentByPostIdAndCommentId(postId, commentId, commentDto);
       return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    //patch replace the partial commentEntity like only body oy only email or only userName

    //single particular post
    //delete comment for post api - /posts/{postId}/comments/{commentId}
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId){
        String message = this.commentService.deleteCommentByPostIdCommentId(postId, commentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //delete comment for post api - /posts/{postId}/comments
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> deleteAllCommentsByPostId(@PathVariable long postId){
       String message = this.commentService.deleteAllCommentsOfPostsFromPostId(postId);
       return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
