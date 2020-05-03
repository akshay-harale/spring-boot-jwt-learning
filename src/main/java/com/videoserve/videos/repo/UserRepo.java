package com.videoserve.videos.repo;

import com.videoserve.videos.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by akshay on 02/05/20
 *
 * @author akshay
 * UserRepo
 */
public interface UserRepo extends CrudRepository<User,Integer> {
    List<User> findAll();
    User findByUsername(String username);
}
