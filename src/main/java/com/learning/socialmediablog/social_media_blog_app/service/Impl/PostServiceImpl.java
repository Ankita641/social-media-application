package com.learning.socialmediablog.social_media_blog_app.service.Impl;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.entity.PostEntity;
import com.learning.socialmediablog.social_media_blog_app.exception.ResourceNotFoundException;
import com.learning.socialmediablog.social_media_blog_app.repository.PostRepository;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import com.learning.socialmediablog.social_media_blog_app.utils.PostEntityMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   // private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PostServiceImpl.class);

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
    public PostDto getPostById(long id) {
        Optional<PostEntity> optionalEntity = this.postRepository.findById(id);
        return optionalEntity.map(postEntity -> postEntityMapper.mapPostEntityToPostDto(postEntity)).orElseThrow(()-> new RuntimeException("Post not found by id: " + id));
    }

    @Override
    public PostDto createPost(PostDto postDtoToBeCreated) {
        PostEntity entityToSave = this.postEntityMapper.mapPostDtoToPostEntity(postDtoToBeCreated);
        return this.postEntityMapper.mapPostEntityToPostDto(this.postRepository.save(entityToSave));

    }

    @Override
    public PostDto updatePost(PostDto postDto, long postIdToBeUpdated) {
        PostEntity postEntityToBeUpdated = this.postRepository.findById(postIdToBeUpdated).orElseThrow(()-> new RuntimeException("Post not found by id: " + postIdToBeUpdated));

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
            //LOGGER.error("Exception while deleting the post by Id: {} " , postIdToBeDeleted);
            return false;
        }
        return true;
    }
}
