package com.example.demo.dataAccess;

import com.example.demo.entities.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser_UserIdAndPost_PostId(Long userId, Long postId);

    List<Comment> findByUser_UserId(Long userId);

    List<Comment> findByPost_PostId(Long postId);

    @Query(value = "select 'commented on', c.post_id, u.user_name from"
            +"comment c left join user u on u.id = c.user_id"
            +"where c.post_id in :postIds limit 5",
            nativeQuery = true)
    List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
}
