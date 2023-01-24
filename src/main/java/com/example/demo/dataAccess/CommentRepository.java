package com.example.demo.dataAccess;

import com.example.demo.entities.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
