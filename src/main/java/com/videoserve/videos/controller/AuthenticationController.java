package com.videoserve.videos.controller;

import com.videoserve.videos.model.*;
import com.videoserve.videos.service.UserService;
import com.videoserve.videos.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * AuthenticationController
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @PostMapping
    public JwtResponse register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        JwtResponse jwtResponse = new JwtResponse(token,
                user.getId(),
                user.getUsername(),
                "fake@email.com",
                new ArrayList<>());
        return jwtResponse;
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody UserDto user){
        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userService.save(user));
    }
}
