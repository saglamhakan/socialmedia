package com.example.demo.services;

import com.example.demo.dataAccess.LikeRepository;
import com.example.demo.entities.Like;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.request.LikeCreateRequest;
import com.example.demo.response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    private final UserService userService;

    private final PostService postService;


    @Autowired
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUser_UserIdAndPost_PostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            list = likeRepository.findByUser_UserId(userId.get());
        } else if (postId.isPresent()) {
            list = likeRepository.findByPost_PostId(postId.get());
        } else
            list = likeRepository.findAll();
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest newLikeRequest) {
        User user = userService.getOneUserById(newLikeRequest.getUserId());
        Post post = postService.getOnePostById(newLikeRequest.getPostId());

        if (user != null && post != null) {

            Like likeToSave = new Like();
            likeToSave.setId(newLikeRequest.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);

            return likeRepository.save(likeToSave);
        } else
            return null;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like updateOneLikeById(Long likeId) {
        Optional<Like> like = likeRepository.findById(likeId);
        if (like.isPresent()) {
            Like likeToUpdate = like.get();
            return likeRepository.save(likeToUpdate);
        } else
            return null;
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
