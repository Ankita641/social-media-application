package com.learning.socialmediablog.social_media_blog_app.controller;

import com.learning.socialmediablog.social_media_blog_app.dto.PostDto;
import com.learning.socialmediablog.social_media_blog_app.payload.PostResponse;
import com.learning.socialmediablog.social_media_blog_app.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/posts")
@Tag(name = "Posts API V2", description = "API for fetching paginated and sorted posts (Version 2)")
public class PostControllerV2 {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Fetch paginated and sorted posts",
            description = "Retrieve a list of posts with pagination and sorting capabilities.",
            parameters = {
                    @Parameter(name = "pageNo", description = "Page number for pagination (default: 0)", example = "0"),
                    @Parameter(name = "pageSize", description = "Number of records per page (default: 10)", example = "10"),
                    @Parameter(name = "sortBy", description = "Field to sort the posts by (default: id)", example = "title"),
                    @Parameter(name = "sortDirection", description = "Sorting direction: ASC or DESC (default: ASC)", example = "DESC")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public PostResponse fetchAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) String sortDirection) {

        return this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection);
    }
}
