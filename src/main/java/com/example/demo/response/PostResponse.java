package com.example.demo.response;

import com.example.demo.entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    Long postId;
    Long userId;
    String userName;
    String title;
    String text;

    List<LikeResponse> postLikes;
    public PostResponse(Post entity, List<LikeResponse> likes){
        this.postId=entity.getPostId();
        this.userId=entity.getUser().getUserId();
        this.userName=entity.getUser().getUserName();
        this.title=entity.getTitle();
        this.text=entity.getText();
        this.postLikes=likes;
    }
}
