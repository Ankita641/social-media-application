package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //delete post

}
