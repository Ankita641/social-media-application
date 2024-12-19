package com.learning.socialmediablog.social_media_blog_app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfiguration {

    //if i do @bean than this ModelMapper object bing register a spring context as a spring bean. spring container inject that
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
