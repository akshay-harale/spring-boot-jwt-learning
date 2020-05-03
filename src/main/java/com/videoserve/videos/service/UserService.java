package com.videoserve.videos.service;

import com.videoserve.videos.model.User;
import com.videoserve.videos.model.UserDto;
import com.videoserve.videos.model.ViewStatus;
import com.videoserve.videos.model.ViewStatusDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * UserService
 */
@Component
public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);

    void updateVideoStatus(ViewStatusDto status);
    ViewStatus getVideoStatus( Long userId,  String videoName);

}
