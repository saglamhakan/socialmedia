package com.example.demo.services;

import com.example.demo.dataAccess.CommentRepository;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.request.CommentCreateRequest;
import com.example.demo.request.CommentUpdateRequest;
import com.example.demo.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;


    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;
        if (userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUser_UserIdAndPost_PostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            comments = commentRepository.findByUser_UserId(userId.get());
        } else if (postId.isPresent()) {
            comments = commentRepository.findByPost_PostId(postId.get());
        } else
            comments = commentRepository.findAll();
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }



    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if (user != null && post != null) {

            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setCreateDate(new Date());
            commentToSave.setText(request.getText());

            return commentRepository.save(commentToSave);

        } else

            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(request.getText());
            return commentRepository.save(commentToUpdate);
        } else
            return null;
    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
