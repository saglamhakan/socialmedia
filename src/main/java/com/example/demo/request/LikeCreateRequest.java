package com.example.demo.request;

import lombok.Data;

@Data
public class LikeCreateRequest {

    Long id;

    Long userId;

    Long postId;
}
