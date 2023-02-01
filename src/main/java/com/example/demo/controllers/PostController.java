package com.example.demo.controllers;

import com.example.demo.entities.Post;
import com.example.demo.request.PostCreateRequest;
import com.example.demo.request.PostUpdateRequest;
import com.example.demo.response.PostResponse;
import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/getAll")
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
       return postService.getAllPosts(userId);

    }

    @GetMapping("/{postId}")
    public PostResponse getOnePost(@PathVariable Long postId){
        return postService.getOnePostByIdWithLikes(postId);
    }

    @PostMapping("/add")
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost){
        return postService.updateOnePostById(postId, updatePost);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId){
         postService.deleteOnePostById(postId);
    }

}
