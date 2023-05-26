package com.example.demo.response;

import com.example.demo.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    Long id;
    Long userId;
    String text;
    String userName;

    public CommentResponse(Comment entity) {
        this.userName=entity.getUser().getUserName();
        this.id = entity.getId();
        this.userId = entity.getUser().getUserId();
        this.text = entity.getText();

    }

}
