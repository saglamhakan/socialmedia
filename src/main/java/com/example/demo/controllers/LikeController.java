package com.example.demo.controllers;

import com.example.demo.entities.Like;
import com.example.demo.request.LikeCreateRequest;
import com.example.demo.response.LikeResponse;
import com.example.demo.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/getAll")
    public List<LikeResponse> getAll(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLikesWithParam(userId,postId);
    }

    @PostMapping("/add")
    public Like createOneLike(@RequestBody LikeCreateRequest newLikeRequest){
        return likeService.createOneLike(newLikeRequest);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@RequestParam Long likeId){
        return likeService.getOneLikeById(likeId);
    }

    @PutMapping("/{likeId}")
    public Like updateOneLike(@PathVariable Long likeId){
        return likeService.updateOneLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeService.deleteOneLikeById(likeId);
    }

}
