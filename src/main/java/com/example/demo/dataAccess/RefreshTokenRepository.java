package com.example.demo.dataAccess;

import com.example.demo.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken getByUser_UserId(Long userId);

}
