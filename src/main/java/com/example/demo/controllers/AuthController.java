package com.example.demo.controllers;

import com.example.demo.entities.RefreshToken;
import com.example.demo.entities.User;
import com.example.demo.request.RefreshRequest;
import com.example.demo.request.UserRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.RefreshTokenService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.refreshTokenService=refreshTokenService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        User user=userService.getOneUserByUserName(loginRequest.getUserName());
        AuthResponse authResponse=new AuthResponse();
        authResponse.setAccessToken("Bearer " +jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setMessage("User login success");
        authResponse.setUserId(user.getUserId());
        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest) {
        AuthResponse authResponse=new AuthResponse();
        if (userService.getOneUserByUserName(registerRequest.getUserName())!= null){
            authResponse.setMessage("Username already in use");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);

        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(registerRequest.getUserName(),registerRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken=jwtTokenProvider.generateJwtToken(auth);

        authResponse.setMessage("User successfully registered");
        authResponse.setAccessToken("Bearer " +jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getUserId());
        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        AuthResponse response = new AuthResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        if (token.getToken().equals(refreshRequest.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token)) {

            User user=token.getUser();


            String jwtToken=jwtTokenProvider.generateJwtTokenByUserName(user.getUserId());


            response.setMessage("Token successfully refreshed");
            response.setAccessToken("Bearer " +jwtToken);
            response.setRefreshToken(refreshTokenService.createRefreshToken(user));
            response.setUserId(user.getUserId());
            return new ResponseEntity<>(response,HttpStatus.OK);

        } else {
            response.setMessage("refresh token is not valid");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


    }

}
