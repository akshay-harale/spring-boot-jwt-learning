package com.videoserve.videos.controller;

import com.videoserve.videos.model.ApiResponse;
import com.videoserve.videos.model.User;
import com.videoserve.videos.model.UserDto;
import com.videoserve.videos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * UserController
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/all", method = RequestMethod.GET)
    @GetMapping
    public String publicAll(){
        return "Public Content";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    @GetMapping
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.",userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.",userService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@RequestBody UserDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
    }

}