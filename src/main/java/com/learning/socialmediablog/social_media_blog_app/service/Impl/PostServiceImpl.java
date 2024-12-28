package com.learning.socialmediablog.social_media_blog_app.service.Impl;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import com.learning.socialmediablog.social_media_blog_app.exception.ResourceNotFoundException;
import com.learning.socialmediablog.social_media_blog_app.payload.PostResponse;
import com.learning.socialmediablog.social_media_blog_app.repository.PostRepository;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import com.learning.socialmediablog.social_media_blog_app.utils.PostEntityMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER =  LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostEntityMapper postEntityMapper;

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();

        if (postEntities != null){
            return postEntities.stream().map(postEntity -> postEntityMapper.mapPostEntityToPostDto(postEntity)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<PostEntity> postEntityList = postRepository.findAll(pageable);

        if (postEntityList != null){

            List<PostDto> postDtoList = postEntityList.stream().map(postEntity -> this.postEntityMapper.mapPostEntityToPostDto(postEntity)).collect(Collectors.toList());

            PostResponse postResponse = PostResponse.builder()
                    .content(postDtoList)
                    .pageNo(postEntityList.getNumber())
                    .pageSize(postEntityList.getSize())
                    .totalElements(postEntityList.getTotalElements())
                    .totalPages(postEntityList.getTotalPages())
                    .isLastPage(postEntityList.isLast())
                    .build();

            return postResponse;
        }
        return null;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable = null;

        if(sortBy != null && sortDirection != null){
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            pageable = PageRequest.of(pageNo,pageSize,sort);
        }else {
            pageable = PageRequest.of(pageNo,pageSize);
        }
        Page<PostEntity> postEntityList = postRepository.findAll(pageable);

        if (postEntityList != null){

            List<PostDto> postDtoList = postEntityList.stream().map(postEntity -> this.postEntityMapper.mapPostEntityToPostDto(postEntity)).collect(Collectors.toList());

            PostResponse postResponse = PostResponse.builder()
                    .content(postDtoList)
                    .pageNo(postEntityList.getNumber())
                    .pageSize(postEntityList.getSize())
                    .totalElements(postEntityList.getTotalElements())
                    .totalPages(postEntityList.getTotalPages())
                    .isLastPage(postEntityList.isLast())
                    .build();

            return postResponse;
        }
        return null;
    }

    @Override
    public PostDto getPostById(long id) {
        Optional<PostEntity> optionalEntity = this.postRepository.findById(id);
        return optionalEntity.map(postEntity -> postEntityMapper.mapPostEntityToPostDto(postEntity)).orElseThrow(()-> new ResourceNotFoundException("Post not found by id: " + id));
    }

    @Override
    public PostDto createPost(PostDto postDtoToBeCreated) {
        PostEntity entityToSave = this.postEntityMapper.mapPostDtoToPostEntity(postDtoToBeCreated);
        return this.postEntityMapper.mapPostEntityToPostDto(this.postRepository.save(entityToSave));

    }

    @Override
    public PostDto updatePost(PostDto postDto, long postIdToBeUpdated) {
        PostEntity postEntityToBeUpdated = this.postRepository.findById(postIdToBeUpdated).orElseThrow(()-> new ResourceNotFoundException("Post not found by id: " + postIdToBeUpdated));

        postEntityToBeUpdated.setContent(postDto.getContent());
        postEntityToBeUpdated.setTitle(postDto.getTitle());
        postEntityToBeUpdated.setDescription(postDto.getDescription());

        PostEntity postEntityUpdated = this.postRepository.save(postEntityToBeUpdated);
        return this.postEntityMapper.mapPostEntityToPostDto(postEntityUpdated);
    }

    @Override
    public boolean deletePostById(long postIdToBeDeleted) {
//       this.postRepository.deleteById(postIdToBeDeleted);
//       return true;

        //second way
        try {

           PostEntity postEntity = this.postRepository.findById(postIdToBeDeleted).orElseThrow(()-> new ResourceNotFoundException("Post not found by id: " + postIdToBeDeleted));
            this.postRepository.delete(postEntity);
        }catch (Exception e){
            LOGGER.error("Exception while deleting the post by Id: {} " , postIdToBeDeleted);
            return false;
        }
        return true;
    }
}
