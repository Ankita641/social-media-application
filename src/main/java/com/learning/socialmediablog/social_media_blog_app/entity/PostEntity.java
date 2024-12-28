package com.learning.socialmediablog.social_media_blog_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    @Size(min = 3, message = "Post Title should have at least 3 Characters")
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Size(min = 5, message = "Post Description should have at least 5 Characters")
    @Column(name = "description")
    private String description;

    @NotEmpty
    @Size(min = 7, message = "Post Content should have at least 7 Characters")
    @Column(name = "content")
    private String content;


    //OneToMany Relationship
    //single post can have multiple comments
    @OneToMany(mappedBy = "postEntity") // field name we pass here from CommentEntity
    private Set<CommentEntity> comments = new HashSet<>();
}
