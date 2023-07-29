package com.example.demo.services;

import com.example.demo.dataAccess.LikeRepository;
import com.example.demo.dataAccess.PostRepository;
import com.example.demo.entities.Like;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.request.PostCreateRequest;
import com.example.demo.request.PostUpdateRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.LikeResponse;
import com.example.demo.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;




    public PostService(PostRepository postRepository, UserService userService
                       ) {
        this.postRepository = postRepository;
        this.userService = userService;

    }


    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post getOnePostByIdWithLikes(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUserById(newPostRequest.getUserId());
        if (user == null)
            return null;

        Post toSave = new Post();
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setCreateDate(new Date());
        toSave.setUser(user);

        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post toUpdate = post.get();
            toUpdate.setText(updatePost.getText());
            toUpdate.setTitle(updatePost.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> posts;
        if(userId.isPresent()){
            posts=postRepository.findByUser_UserId(userId.get());
        }else
          posts = postRepository.findAll();
        return posts.stream().map(post -> new PostResponse(post)).collect(Collectors.toList());
    }

    }

