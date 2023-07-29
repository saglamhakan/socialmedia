package com.example.demo.response;

import com.example.demo.entities.User;
import lombok.Data;

@Data
public class UserResponse {
    Long userId;
    String userName;

    public UserResponse(User entity) {
        this.userId =entity.getUserId();
        this.userName = entity.getUserName();
    }
}
