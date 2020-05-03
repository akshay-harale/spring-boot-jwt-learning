package com.videoserve.videos.service;

import com.videoserve.videos.model.User;
import com.videoserve.videos.model.UserDto;
import org.springframework.stereotype.Component;

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
}
