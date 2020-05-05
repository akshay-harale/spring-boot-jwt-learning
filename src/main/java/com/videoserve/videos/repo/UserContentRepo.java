package com.videoserve.videos.repo;

import com.videoserve.videos.model.UserContent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by akshay on 05/05/20
 *
 * @author akshay
 * UserContentRepo
 */
public interface UserContentRepo extends CrudRepository<UserContent,Integer> {
    List<UserContent> findByUserName(String userName);
    List<UserContent> findByFileName(String fileName);
}
