package com.example.demo.services;

import com.example.demo.dataAccess.PostRepository;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.request.PostCreateRequest;
import com.example.demo.request.PostUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

   private final PostRepository postRepository;

   private final UserService userService;

    @Autowired
    public PostService( PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService=userService;
    }


    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent())
            return postRepository.getByUser_UserId(userId.get());
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post createOnePost(PostCreateRequest newPostRequest) {
      User user =  userService.getOneUserById(newPostRequest.getUserId());
      if (user==null)
          return null;

      Post toSave=new Post();
      toSave.setPostId(newPostRequest.getId());
      toSave.setText(newPostRequest.getText());
      toSave.setTitle(newPostRequest.getTitle());
      toSave.setUser(user);

        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post=postRepository.findById(postId);
        if (post.isPresent()){
            Post toUpdate=post.get();
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
}
