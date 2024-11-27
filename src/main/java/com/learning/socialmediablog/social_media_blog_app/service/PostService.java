package com.learning.socialmediablog.social_media_blog_app.service;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.payload.PostResponse;

import java.util.List;

public interface PostService {

    //Get all the post
    List<PostDto> getAllPosts();

    PostResponse getAllPosts(int pageNo, int pageSize);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);

    //Get post by id
    PostDto getPostById(long id);

    //Create post
    PostDto createPost(PostDto postDtoToBeCreated);

    //Update post
    PostDto updatePost(PostDto postDto, long postIdToBeUpdated);

    //Delete post by id
    boolean deletePostById(long postIdToBeDeleted);
}
