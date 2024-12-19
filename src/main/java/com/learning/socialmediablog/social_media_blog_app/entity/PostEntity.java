package com.learning.socialmediablog.social_media_blog_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;


    //OneToMany Relationship
    //single post can have multiple comments
    @OneToMany(mappedBy = "postEntity") // field name we pass here from CommentEntity
    private Set<CommentEntity> comments = new HashSet<>();
}
