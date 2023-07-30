package com.example.demo.response;

import com.example.demo.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long likeId;
    Long userId;
    Long postId;


    public LikeResponse(Like entity){
        this.likeId =entity.getLikeId();
        this.postId=entity.getPost().getPostId();
        this.userId=entity.getUser().getUserId();
    }
}
