package com.example.demo.services;

import com.example.demo.dataAccess.CommentRepository;
import com.example.demo.dataAccess.LikeRepository;
import com.example.demo.dataAccess.PostRepository;
import com.example.demo.dataAccess.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private LikeRepository likeRepository;

    private CommentRepository commentRepository;

    private PostRepository postRepository;


    @Autowired
    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    public List<UserResponse> getAllUsers() {

        List<User> users=userRepository.findAll();
        List<UserResponse> userResponses=new ArrayList<UserResponse>();

        for (User user:users){
            UserResponse foundUser=new UserResponse(user);
            foundUser.setUserName(user.getUserName());
            userResponses.add(foundUser);
        }

        return userResponses;
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        } else
            return null;
    }

    public void deleteById(Long userId) {
        try {
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("+User "+userId+" doesn't exist" );
        }
    }
    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if (postIds.isEmpty())
            return null;
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result=new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
