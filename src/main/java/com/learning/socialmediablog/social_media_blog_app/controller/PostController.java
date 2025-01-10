package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/posts")    // it is main entry point for server where we define an api. this is base entry point
@Tag(name = "Posts API", description = "API for managing posts. Allows user to fetch, create, update and delete posts.")
public class PostController {

    @Autowired
    private PostService postService;

    //get all posts
    //v1/api/posts
    @Operation(summary = "Fetch all posts", description = "Retrieve a list of all blog posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of posts retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<PostDto> fetchAllPosts() {
        return this.postService.getAllPosts();
    }

    //get post by id
    //v1/api/posts/{postId}
    @Operation(summary = "Fetch post by ID", description = "Retrieve a specific post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
   // @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public PostDto fetchPostById(@Parameter(description = "ID of the Post to be fetched", required = true)
                                     @PathVariable long postId) {
        return this.postService.getPostById(postId);
    }

    //create post
    //v1/api/posts
    @Operation(summary = "Create a new post", description = "Create a new post by providing the necessary details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto savePost(@Parameter(description = "Post details to create a new Post", required = true)
                             @Valid @RequestBody PostDto postDto) {

        return this.postService.createPost(postDto);
    }

    //update post
    //v1/api/posts/{postId}
    @Operation(summary = "Update an existing post", description = "Update a post by providing the ID and updated details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto updatePost(@Parameter(description = "Update Post details", required = true)
                               @Valid   @RequestBody PostDto postDtoToBeUpdated,
                              @Parameter(description = "ID of the Post to be updated", required = true)
                              @PathVariable long postId) {
        return this.postService.updatePost(postDtoToBeUpdated, postId);
    }


    //delete post
    //v1/api/posts/{postId}
    @Operation(summary = "Delete a post by ID", description = "Delete a post by providing its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@Parameter(description = "ID of the Post to be deleted", required = true)
                                                 @PathVariable long postId) {
        boolean isDeleted = this.postService.deletePostById(postId);
        if (isDeleted) {
            return ResponseEntity.ok("Post " + postId + " delete successfully");
        } else {
            return new ResponseEntity<>("Error while deleting Post " + postId, HttpStatus.NOT_FOUND);
        }
    }
}
