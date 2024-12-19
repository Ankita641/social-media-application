package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/posts")    // it is main entry point for server where we define an api. this is base entry point
public class PostController {

    @Autowired
    private PostService postService;

    //get all posts
    //v1/api/posts
    @GetMapping
    public List<PostDto> fetchAllPosts(){
       return this.postService.getAllPosts();
    }

    //get post by id
    //v1/api/posts/{postId}
    @GetMapping("/{postId}")
    public PostDto fetchPostById(@PathVariable long postId){
        return this.postService.getPostById(postId);
    }


    //create post
    //v1/api/posts
    @PostMapping
    public PostDto savePost(@RequestBody  PostDto postDto){
        return this.postService.createPost(postDto);
    }

    //update post
    //v1/api/posts/{postId}
    @PutMapping("/{postId}")
    public PostDto updatePost(@RequestBody PostDto postDtoToBeUpdated, @PathVariable long postId){
        return this.postService.updatePost(postDtoToBeUpdated,postId);
    }


    //delete post
    //v1/api/posts/{postId}
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId){
        boolean isDeleted = this.postService.deletePostById(postId);
        if (isDeleted){

            return ResponseEntity.ok("Post " + postId + " delete successfully");
        }else {
            return new ResponseEntity<>("Error while deleting Post " + postId , HttpStatus.NOT_FOUND);
        }
    }

}
