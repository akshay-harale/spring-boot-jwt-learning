package com.videoserve.videos.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.videoserve.videos.model.*;
import com.videoserve.videos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.synchronoss.cloud.nio.multipart.Multipart;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PatchMapping("/video_status")
    public ResponseEntity<String> updateTime(@RequestBody ViewStatusDto status) {
        try {
            userService.updateVideoStatus(status);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @GetMapping("/video_status/{userId}/{videoName}")
    public ResponseEntity<?> getStatus(@PathVariable Long userId,@PathVariable String videoName) {
        try {

            ViewStatus videoStatus = userService.getVideoStatus(userId,videoName);
            if(videoStatus !=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(videoStatus);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}