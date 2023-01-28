package com.example.demo.dataAccess;

import com.example.demo.entities.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUser_UserIdAndPost_PostId(Long userId, Long postId);

    List<Comment> findByUser_UserId(Long userId);

    List<Comment> findByPost_PostId(Long postId);


}
