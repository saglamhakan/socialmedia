package com.example.demo.request;

import lombok.Data;

import java.util.Date;

@Data
public class PostCreateRequest {


    String text;

    String title;

    Long userId;

    Date createDate;

}
