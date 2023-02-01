package com.example.demo.dataAccess;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Like;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByUser_UserIdAndPost_PostId(Long userId, Long postId);

    List<Like> findByUser_UserId(Long userId);

    List<Like> findByPost_PostId(Long postId);

    @Query(value = "select 'liked', l.post_id, u.user_name from"
            +"p_like l left join user u on u.id = l.user_id"
                    +"where l.post_id in :postIds limit 5",
            nativeQuery = true)
    List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);

}
