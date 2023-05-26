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





    public PostResponse(Post post) {
        this.postId=post.getPostId();
        this.userId=post.getUser().getUserId();
        this.userName=post.getUser().getUserName();
        this.text= post.getText();
        this.title= post.getTitle();



    }
}

