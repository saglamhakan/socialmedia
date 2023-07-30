package com.example.demo.request;

import lombok.Data;

@Data
public class CommentCreateRequest {

    Long userId;
    Long postId;
    String text;
}
