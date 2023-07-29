package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.request.UserCreateRequest;
import com.example.demo.request.UserUpdateRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public User createUser(@RequestBody UserCreateRequest newUser) {
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        User user=userService.getOneUserById(userId);
        if (user==null){
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }

    @PutMapping("/update/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody UserUpdateRequest newUser) {
        return userService.updateOneUser(userId, newUser);

    }

    @DeleteMapping("/delete/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
       return userService.getUserActivity(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound(){

    }

}


