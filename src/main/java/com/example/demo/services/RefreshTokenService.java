package com.example.demo.services;

import com.example.demo.dataAccess.RefreshTokenRepository;
import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository=refreshTokenRepository;
    }

    public String createRefreshToken(User user){
        RefreshToken token=new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public boolean isRefreshExpired(RefreshToken token){
        return token.getExpiryDate().before(new Date());
    }


    public RefreshToken getByUser(Long userId) {
      return refreshTokenRepository.getByUser_UserId(userId);

    }
}
