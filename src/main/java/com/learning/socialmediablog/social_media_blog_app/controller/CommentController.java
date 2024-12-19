package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.CommentDto;
import com.learning.socialmediablog.social_media_blog_app.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comments API", description = "API for managing comments on posts. Allows user to fetch, create, update and delete comments for posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //Create Comment  - /posts/{postId}/comments
    @Operation(summary = "Create a comment", description = "Create a new comment for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        CommentDto saveCommentDto = this.commentService.createCommentForPost(postId, commentDto);
        return new ResponseEntity<>(saveCommentDto, HttpStatus.CREATED);
    }

    //get all Comments for Post Api - //posts/{postId}/comments
    @Operation(summary = "Get all comments for a post", description = "Retrieve all comments for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable long postId) {
        List<CommentDto> commentDtoList = this.commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    //get single Comment for Post api - /posts/{postId}/comments/{commentId}
    @Operation(summary = "Get a specific comment for a post", description = "Retrieve a specific comment by post ID and comment ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found", content = @Content)
    })
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> fetchCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto commentDto = this.commentService.getCommentByPostIdAndCommentId(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //update comment rest api-   /posts/{postId}/comments/{commentId}
    @Operation(summary = "Update a comment", description = "Update a comment by providing post ID, comment ID, and updated details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found", content = @Content)
    })
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentByPostIdAndCommentId(
            @PathVariable long postId,
            @PathVariable long commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = this.commentService.updateCommentByCommentByPostIdAndCommentId(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    //patch replace the partial commentEntity like only body oy only email or only userName

    //single particular post
    //delete comment for post api - /posts/{postId}/comments/{commentId}
    @Operation(summary = "Delete a specific comment", description = "Delete a specific comment for a post by providing post ID and comment ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found", content = @Content)
    })
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId) {
        String message = this.commentService.deleteCommentByPostIdCommentId(postId, commentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //delete comment for post api - /posts/{postId}/comments
    @Operation(summary = "Delete all comments for a post", description = "Delete all comments associated with a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All comments deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> deleteAllCommentsByPostId(@PathVariable long postId) {
        String message = this.commentService.deleteAllCommentsOfPostsFromPostId(postId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
