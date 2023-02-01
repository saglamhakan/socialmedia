package com.example.demo.response;

import com.example.demo.entities.Like;
import lombok.Data;

import java.util.List;

@Data
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;

    List<LikeResponse> list;

    public LikeResponse(Like entity){
        this.id=entity.getId();
        this.postId=entity.getPost().getPostId();
        this.userId=entity.getUser().getUserId();
    }
}
