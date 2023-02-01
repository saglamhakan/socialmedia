package com.example.demo.dataAccess;

import com.example.demo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> getByUser_UserId(Long userId);

    @Query(value = "select id from post where user_id =:userId order by create_date desc limit 5",
            nativeQuery = true)
    List<Long> findTopByUserId(@Param("userId") Long userId);
}
